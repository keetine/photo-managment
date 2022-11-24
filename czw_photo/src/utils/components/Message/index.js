import { createApp } from "vue";
import MessageComponent from './MessageBox.vue';

let MessageDom;

const Message = (text) =>{
    return new Promise(()=>{
        MessageDom = createApp(MessageComponent).mount(document.createElement("div"));
        MessageDom.text = Object.assign({},MessageDom.text,text);
        document.body.appendChild(MessageDom.$el);
    })
}

export default Message
