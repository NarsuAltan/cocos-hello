
cc.Class({
    extends: cc.Component,

    properties: {
        label: {
            default: null,
            type: cc.Label
        },
        // defaults, set visually when attaching this script to the Canvas
        text: 'Hello, World!',

        editbox: {
            default: null,
            type: cc.EditBox
        }
    },

    // use this for initialization
    onLoad: function () {

    },

    // called every frame
    update: function (dt) {

    },


    uploadImage: function () {
        Bridge.pickImage();
    },

    showAd: function () {
        Bridge.showAd();
    },
            
    send: function () {
        let txt = this.editbox.string
        Bridge.send(txt)
        this.editbox.string = ""
    }
});
