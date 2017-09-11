#include <jni.h>
#include "substrate.h"
#include <android/log.h>
#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>
#include <sys/types.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/system_properties.h>
#include <sys/socket.h>

#define TAG "HOOKTEST"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define BUFLEN 1024
//MSConfig(MSFilterLibrary, "libdvm.so");
MSConfig(MSFilterLibrary, "libc.so");

bool (*_dvmLoadNativeCode)(char* pathName, void* classLoader, char** detail);

bool fake_dvmLoadNativeCode(char* soPath, void* classLoader, char** detail) {
	LOGD("fake_dvmLoadNativeCode soPath:%s", soPath);
	return _dvmLoadNativeCode(soPath, classLoader, detail);
}

////Substrate entry point
//MSInitialize{
//    LOGD("Substrate initialized.");
//    MSImageRef image;
//    image = MSGetImageByName("/system/lib/libdvm.so"); // ����·��
//    if (image != NULL)
//    {
//    	LOGD("dvm image: 0x%08X", (void*)image);
//
//        void * dvmload = MSFindSymbol(image, "_Z17dvmLoadNativeCodePKcP6ObjectPPc");
//        if(dvmload == NULL)
//        {
//            LOGD("error find dvmLoadNativeCode.");
//        }
//        else
//        {
//        	MSHookFunction(dvmload,(void*)&fake_dvmLoadNativeCode,(void **)&_dvmLoadNativeCode);
//			LOGD("hook dvmLoadNativeCode sucess.");
//        }
//    }
//    else{
//        LOGD("can not find libdvm.");
//    }
//}

static char AppName[256] = { 0 };
char* GetAppName() {
	FILE *fp = NULL;

//    pid_t pid = getpid();
//    char str[15];
//    sprintf(str, "%d", pid);
	char path[256] = { 0 };
	pid_t pid = getpid();
	char str[15];
	sprintf(str, "%d", pid);
	memset(path, 0, sizeof(path));
	strcat(path, "/proc/");
	strcat(path, str);
	strcat(path, "/cmdline");

	fp = fopen(path, "r");
	if (fp) {
		memset(AppName, sizeof(AppName), 0);
		fread(&AppName, sizeof(AppName), 1, fp);
		fclose(fp);
	}
	return AppName;
}
int (*old__system_property_get)(const char *name, char *value);
int new__system_property_get(const char *name, char *value) {
	GetAppName();
	if (AppName != NULL
			&& ((strstr(AppName, "com.example.hellojni"))
					|| (strstr(AppName, "com.donson.leplay.store2")))) {
		LOGD("new__system_property_get name1:%s, value1:%s, ===========-------------pacname:%s", name, value, AppName);
	}
	if (strstr(name, "ro.serialno")||strstr(name,"ro.boot.serialno"))
		return strlen(strcpy(value, "fo5mh091"));
	else if (strstr(name, "ro.product.model"))
		return strlen(strcpy(value, "CAZ-AL10"));
	else if (strstr(name, "ro.ril.oem.imei"))
		return strlen(strcpy(value, "863531037180387"));
	else if (strstr(name,"ro.ril.miui.imei0"))
		return strlen(strcpy(value, "863531037180387"));
	else if (strstr(name, "persist.radio.imei"))
		return strlen(strcpy(value, "863531037180387"));
	else if (strstr(name, "ro.product.brand"))
		return strlen(strcpy(value, "HUAWEI"));
	else if (strstr(name, "gsm.version.baseband"))
			return strlen(strcpy(value, "DM_BASE_13A_8803G_W13.44 | 2014012009233"));
	else if (strstr(name, "ro.build.id"))
			return strlen(strcpy(value, "KTU84P"));
	else if (strstr(name, "ro.build.display.id"))
			return strlen(strcpy(value, "KTU84P"));
	else if (strstr(name, "ro.build.fingerprint"))
				return strlen(strcpy(value, "HUAWEI/HUAWEI/CAZ-AL10:4.4.4/KTU84P/MRA58K:user/release-keys"));
	else if (strstr(name, "ro.build.version.release"))
				return strlen(strcpy(value, "4.4.4"));
	else if (strstr(name, "ro.product.manufacturer"))
				return strlen(strcpy(value, "HUAWEI"));
	else if (strstr(name,"gsm.version.ril-impl"))
		return strlen(strcpy(value,"android huawei-ril for HUAWEI 1.1"));
	else if (strstr(name,"net.hostname"))
		return strlen(strcpy(value,"CAZ-AL10-huaweishouji"));
	else if(strstr(name,"ro.build.host"))
		return strlen(strcpy(value,"compiler02301"));
	else if(strstr(name,"persist.service.bdroid.bdaddr"))
		return  strlen(strcpy(value,"f8:a4:5f:6f:7:55"));
	else
		return old__system_property_get(name, value);
}
//FILE *fopen(const char *filename, const char *modes)
//int (*old_flopen)(const char *filename, const char *modes);
int (*old_fopen)(const char* path, const char* mode);
int new_fopen(const char* path, const char* mode) {

//	unsigned lr;
//	GETLR(lr);
	if (AppName != NULL
				&& ((strstr(AppName, "com.example.hellojni"))
						|| (strstr(AppName, "com.donson.leplay.store2")))) {
		LOGD("callmy fopen!!:%d,,PATH:%s", getpid(), path);
	}
	if (strstr(path, "/sys/class/net/wlan0/address")) {
		return	old_fopen("/sdcard/xx/file/address",mode);
	} else {
		return old_fopen(path,mode);
	}
}
int (*old_open)(const char* path, int flag);
int new_open(const char* path, int flag) {

//	unsigned lr;
//	GETLR(lr);
	if (AppName != NULL
				&& ((strstr(AppName, "com.example.hellojni"))
						|| (strstr(AppName, "com.donson.leplay.store2")))) {
		LOGD("open!!:%d,,PATH:%s", getpid(), path);
	}
	if (strstr(path, "/sys/class/net/wlan0/address")) {
		return	old_open("/sdcard/xx/file/address",flag);
	} else {
		return old_open(path,flag);
	}
}
void (*oldConnect)(int, const sockaddr *, socklen_t);
void newConnect(int socket, const sockaddr *address, socklen_t length) {
	if (AppName != NULL
					&& ((strstr(AppName, "com.example.hellojni"))
							|| (strstr(AppName, "com.donson.leplay.store2")))) {
	LOGD("connect!!:%d,,sockaddr:%s", socket, address->sa_data);
	}
	return oldConnect(socket, address, length);
}
int (*oldsocket)(int domain, int type, int protocol);
int newsocket(int domain, int type, int protocol){

	if (AppName != NULL&& ((strstr(AppName, "com.example.hellojni"))
							|| (strstr(AppName, "com.donson.leplay.store2")))) {
	LOGD("socket domain!!:%d,,type:%d,protocol :%d", domain, type,protocol);
	}
		return oldsocket(domain, type, protocol);
}
char (*old_strcpy)(char *dest, const char *src);
char new_strcpy(char *dest, const char *src){
	if (AppName != NULL&& ((strstr(AppName, "com.example.hellojni"))|| (strstr(AppName, "com.donson.leplay.store2")))) {
			LOGD("new_strcpy dest!!:%d,,src:%s",dest, src);
		}
			return old_strcpy(dest,src);
}
void (*old_popen)(const char *command, const char *modes);
void new_popen(const char *command, const char *modes){
	if (AppName != NULL&& ((strstr(AppName, "com.example.hellojni"))|| (strstr(AppName, "com.donson.leplay.store2")))) {
				LOGD("new_popen dest!!:%d,,src:%s",command, modes);
			}
				return old_popen(command,modes);
}
char (*old_strstr)(const char *haystack, const char *needle);
char new_strstr(const char *haystack, const char *needle){
	if (AppName != NULL&& ((strstr(AppName, "com.example.hellojni"))|| (strstr(AppName, "com.donson.leplay.store2")))) {
					LOGD("new_strstr haystack!!:%d,,needle:%s",haystack, needle);
				}
					return old_strstr(haystack,needle);
}
//int strcmp(const char *s1, const char *s2)
//�������
MSInitialize
{

	MSImageRef image = MSGetImageByName("/system/lib/libc.so");
	char * bufferProcess = (char*) calloc(256, sizeof(char));


	if (image != NULL) {
		void *hook__system_property_read = MSFindSymbol(image,
				"__system_property_get");
		if (hook__system_property_read)
			MSHookFunction(hook__system_property_read,
					(void*) &new__system_property_get,
					(void **) &old__system_property_get);
		else
			LOGD("hook dvmLoadNativeCode NULL.");
		//LOGE("error find __system_property_read ");
	}

	if (image != NULL) {
		void *hook_fopen = MSFindSymbol(image, "fopen");
		if (hook_fopen)
			MSHookFunction(hook_fopen, (void*) &new_fopen,
					(void **) &old_fopen);
		else
			LOGD("hook dvmLoadNativeCode NULL.");
		//LOGE("error find __system_property_read ");
	}

	if (image != NULL) {
			void *hook_open = MSFindSymbol(image, "open");
			if (hook_open)
				MSHookFunction(hook_open, (void*) &new_open,
						(void **) &old_open);
			else
				LOGD("hook dvmLoadNativeCode NULL.");
	}

	void *connect = MSFindSymbol(image, "connect");
	MSHookFunction(connect, (void*) &newConnect, (void **) &oldConnect);


	void *sock = MSFindSymbol(image, "socket");
	MSHookFunction(sock, (void*) &newsocket, (void **) &oldsocket);


//	void *strcpy = MSFindSymbol(image, "strcpy");
//	MSHookFunction(strcpy, (void*) &new_strcpy, (void **) &old_strcpy);

//	void *popen1 = MSFindSymbol(image, "popen");
//	MSHookFunction(popen1, (void*) &new_popen, (void **) &old_popen);

//	void *strstr = MSFindSymbol(image, "strstr");
//	MSHookFunction(strstr, (void*) &new_strstr, (void **) &old_strstr);
}
int GetPropValue(const char* name,char *value)
{
    if(!name) return -1;
    bool bGet=false;
    if(!NeedHook()) return -1;
    if(strstr(name,"ro.super.version")) bGet=SetPropResultValue(value,SUPERVERSION);
    else if(strstr(name,"ro.product.model")) bGet=GetCatValue("model",value);
    else if(strstr(name,"ro.serialno")) bGet=GetCatValue("serial",value);
    else if(strstr(name,"ro.boot.serialno")) bGet=GetCatValue("serial",value);
        //else if(strstr(name,"ro.board.platform")) bGet=GetCatValue("brand",value);
    else if(strstr(name,"ro.build.version.release")) bGet=GetCatValue("version",value);
    else if(strstr(name,"ro.product.manufacturer")) bGet=GetCatValue("manufacturer",value);
    else if(strstr(name,"ro.product.name")) bGet=GetCatValue("product",value);
    else if(strstr(name,"ro.hardware")) bGet=GetCatValue("model",value);
    else if(strstr(name,"ro.product.board")) bGet=GetCatValue("board",value);
    else if(strstr(name,"ro.product.brand")) bGet=GetCatValue("brand",value);
    else if(strstr(name,"ro.build.custom.display.id")) bGet=GetCatValue("model",value);
    else if(strstr(name,"ro.build.display.id")) bGet=GetCatValue("id",value);
    else if(strstr(name,"ro.product.device")) bGet=GetCatValue("device",value);
    else if(strstr(name,"ro.build.fingerprint")) bGet=GetCatValue("fingerprint",value);
    else if(strstr(name,"ro.build.description")) bGet=GetCatValue("description",value);
    else if(strstr(name,"ro.mediatek.version.release")) bGet=GetCatValue("display",value);
    else if(strstr(name,"ro.build.product")) bGet=GetCatValue("model",value);
    else if(strstr(name,"ro.build.internal.display.id")) bGet=GetCatValue("model",value);
    else if(strstr(name,"persist.radio.cfu.iccid.0")) bGet=GetCatValue("simserial",value);
    else if(strstr(name,"ril.iccid.sim1")) bGet=GetCatValue("simserial",value);
    else if(strstr(name,"persist.radio.imei")) bGet=GetCatValue("imei",value);
    else if(strstr(name,"ro.ril.oem.imei")) bGet=GetCatValue("imei",value);
    else if(strstr(name,"ro.ril.oem.sno")) bGet=GetCatValue("sno",value);
    else if(strstr(name,"ro.ril.oem.meid")) bGet=GetCatValue("meid",value);
    else if(strstr(name,"persist.radio.meid")) bGet=GetCatValue("meid",value);
    else if(strstr(name,"gsm.sim.state")) bGet=SetPropResultValue(value,"READY");
    else if(strstr(name,"gsm.operator.alpha")) bGet=GetCatValue("simoperator",value);
    else if(strstr(name,"gsm.sim.operator.alpha")) bGet=GetCatValue("networkoperatorname",value);
    else if(strstr(name,"gsm.operator.numeric")) bGet=GetCatValue("simoperator",value);
    else if(strstr(name,"permanent.radio.modem")) bGet=GetCatValue("radiomodem",value);
    else if(strstr(name,"persist.radio.modem")) bGet=SetPropResultValue(value,"TD");
    else if(strstr(name,"ro.build.host")) bGet=GetCatValue("model",value);
    else if(strstr(name,"ro.ril.miui.imei")) bGet=GetCatValue("imei",value);
    else if(strstr(name,"ro.runtime.firstboot")) bGet=GetCatValue("firstboot",value);
    else if(strstr(name,"persist.sys.xtrainject.time")) bGet=GetCatValue("buildtime",value);
    else if(strstr(name,"ro.ckis")) bGet=SetPropResultValue(value,"OK!");
    else if(strstr(name,"ro.ckis.model")) bGet=GetCatValue("model",value);
    else if(strstr(name,"ro.ckis.apilevel")) bGet=GetCatValue("apilevel",value);
    else if(strstr(name,"ro.build.date.utc")) bGet=GetCatValue("buildtime",value);
    else if(strstr(name,"ro.product.cuptsm")) bGet=SetPropResultValue(value,"%s|ESE|02|01",brand);
    else if(strstr(name,"gsm.version.ril-impl")) bGet=SetPropResultValue(value,"android %s-ril for %s 1.1",brand,brand);
    else if(strstr(name,"rild.libpath")) bGet=SetPropResultValue(value,"/system/lib/libril-%s-sprd.so",brand);
    else if(strstr(name,"persist.sys.NV_PROFILE_MODEL")) bGet=SetPropResultValue(value,model);
    else if(strstr(name,"persist.service.bdroid.bdaddr")) bGet=GetCatValue("bluemac",value);
    else if(strstr(name,"persist.sys.klo.rec_start")) bGet=GetCatValue("firstboot",value);
    else if(strstr(name,"net.hostname")) bGet=SetPropResultValue(value,"%s-Android",model);
    else if(strstr(name,"ril.timediff")) bGet=GetCatValue("timediff",value);
    else if(strstr(name,"gsm.version.baseband")) bGet=GetCatValue("radioversion",value);
    else if(strstr(name,"ro.build.version.sdk")) bGet=GetCatValue("apilevel",value);
    else if(strstr(name,"ro.build.id")) bGet=GetCatValue("id",value);
    else if(strstr(name,"dhcp.wlan0.dns1") || strstr(name,"dhcp.wlan0.gateway") || strstr(name,"dhcp.wlan0.server") || strstr(name,"net.dns1"))
        bGet=SetPropResultValue(value,"192.168.%d.%d",GetRand(nSeed^0x1111,0,254),GetRand(nSeed^0x2222,1,254));
    else if(strstr(name,"dhcp.wlan0.ipaddress")) bGet=SetPropResultValue(value,"192.168.%d.%d",GetRand(nSeed^0x3333,0,254),GetRand(nSeed^0x4444,1,254));
    else if(strstr(name,"dhcp.wlan0.dns2") || strstr(name,"net.dns2")) bGet=SetPropResultValue(value,"192.168.%d.%d",GetRand(nSeed^0x5555,0,254),GetRand(nSeed^0x6666,1,254));
    else if(strstr(name,"persist.sys.NV_DISPXRES")) bGet=GetCatValue("swidth",value);
    else if(strstr(name,"persist.sys.NV_DISPYRES")) bGet=GetCatValue("sheight",value);
    int result=-1;
    if(bGet)
    {
        result=strlen(value);
        LOGI("GetPropValue::: Call:%s|%s|%d",name,value,result);
    }
    return result;
}

bool GetCatValue(const char* name,char *value){

}

