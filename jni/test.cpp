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
//    image = MSGetImageByName("/system/lib/libdvm.so"); // 绝对路径
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
		return strlen(strcpy(value, "GRA-UL10"));
	else if (strstr(name, "ro.ril.oem.imei"))
		return strlen(strcpy(value, "869381021542042"));
	else if (strstr(name, "persist.radio.imei"))
		return strlen(strcpy(value, "869381021542042"));
	else if (strstr(name, "ro.product.brand"))
		return strlen(strcpy(value, "HUAWEI"));
	else if (strstr(name, "gsm.version.baseband"))
			return strlen(strcpy(value, "HUAWEI"));
	else if (strstr(name, "ro.product.brand"))
			return strlen(strcpy(value, "HUAWEI"));
	else if (strstr(name, "ro.product.brand"))
			return strlen(strcpy(value, "HUAWEI"));
	else
		return old__system_property_get(name, value);
}

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
		LOGD("myopen!!:%d,,PATH:%s", getpid(), path);
	}
	if (strstr(path, "/sys/class/net/wlan0/address")) {
		return	old_open("/sdcard/xx/file/address",flag);
	} else {
		return old_open(path,flag);
	}
}

//程序入口
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
			//LOGE("error find __system_property_read ");
		}
}

