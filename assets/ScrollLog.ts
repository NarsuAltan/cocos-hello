// Learn TypeScript:
//  - https://docs.cocos.com/creator/2.4/manual/en/scripting/typescript.html
// Learn Attribute:
//  - https://docs.cocos.com/creator/2.4/manual/en/scripting/reference/attributes.html
// Learn life-cycle callbacks:
//  - https://docs.cocos.com/creator/2.4/manual/en/scripting/life-cycle-callbacks.html

const {ccclass, property} = cc._decorator;

@ccclass
export default class ScrollLog extends cc.Component {

    @property(cc.Label)
    item: cc.Label = null;

    @property(cc.Node)
    content: cc.Node = null;

    // LIFE-CYCLE CALLBACKS:

    // onLoad () {}

    start () {
        window.log2view('first log----------')
    }

    // update (dt) {}

    log(msg: string){
        let node = cc.instantiate(this.item.node)
        node.parent = this.content
        node.getComponent(cc.Label).string = msg
        node.active = true
    }
}

//@ts-ignore
window.log2view = function(msg){
    cc.find('Canvas/ScrollLog').getComponent(ScrollLog).log(msg)
}