#include <jni.h>
#include "substrate.h"
#include <android/log.h>
#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>
#include <sys/types.h>
#include <string.h>
#include <sys/stat.h>

#define TAG "HOOKTEST"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)

//MSConfig(MSFilterLibrary, "libdvm.so");
MSConfig(MSFilterLibrary, "libc.so");


bool (*_dvmLoadNativeCode)(char* pathName, void* classLoader, char** detail);

bool fake_dvmLoadNativeCode(char* soPath, void* classLoader, char** detail)
{
	LOGD("fake_dvmLoadNativeCode soPath:%s", soPath);
	return _dvmLoadNativeCode(soPath,classLoader,detail);
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


File (*old__system_property_get)(const char *name, char *value);
File new__system_property_get(const char *name, char *value)
{
	LOGD("new__system_property_read  name:%s, value:%s",name,value);
	if(strstr(name,"ro.serialno")) return strlen(strcpy(value,"201415486"));
	else if(strstr(name,"ro.product.model")) return strlen(strcpy(value,"M2"));
	else return old__system_property_get(name,value);

//    int result=old__system_property_get(name,value);
//    return result;
}

int (*old_flopen)(const char *filename, const char *modes);
int
//程序入口
MSInitialize
{
    MSImageRef image = MSGetImageByName("/system/lib/libc.so");
    if (image != NULL)
    {
        void *hook__system_property_read=MSFindSymbol(image,"__system_property_get");
        if(hook__system_property_read) MSHookFunction(hook__system_property_read,(void*)&new__system_property_get,(void **)&old__system_property_get);
        else LOGD("hook dvmLoadNativeCode NULL.");//LOGE("error find __system_property_read ");
    }
    if (image != NULL)
        {
            void *hook_flopen=MSFindSymbol(image,"fopen");
            if(hook_flopen) MSHookFunction(hook_flopen,(void*)&new__system_property_get,(void **)&old__system_property_get);
            else LOGD("hook dvmLoadNativeCode NULL.");//LOGE("error find __system_property_read ");
        }
}

















