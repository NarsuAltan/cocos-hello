var bridgeVar = cc.Class({
    extends: cc.Component,

    statics: {


        _IOS_CLASS_NAME: 'PlatformIosApi', //ios类名
        _AND_CLASS_NAME: 'org/cocos2dx/javascript/AppActivity', //android类名
        _uploadUrl: 'https://baidu.com/upload',

        pickImage: function () {
            if(cc.sys.os == cc.sys.OS_ANDROID)
            {
                jsb.reflection.callStaticMethod(this._AND_CLASS_NAME, 'pickImage', '()V');

            }
        },

        pickImageCallback: function (imagePath) {
            var param = {
                uid : "666"
            }
            this.uploadImage(this._uploadUrl, imagePath,JSON.stringify(param));
        },

        uploadImage: function (url, imagePath, param) {
            if(cc.sys.os == cc.sys.OS_ANDROID)
            {
                var result = jsb.reflection.callStaticMethod(this._AND_CLASS_NAME, 'uploadImage', '(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;', url, imagePath,param);
                console.log(" uploadImage " , result)
            }
        },
        showAd: function () {
            if (cc.sys.os == cc.sys.OS_ANDROID) {
                jsb.reflection.callStaticMethod(this._AND_CLASS_NAME, 'showAd', '()V');
            }
            
        }
    }




});


window.Bridge = bridgeVar;
