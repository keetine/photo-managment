<template>
    <div class="container" v-show="show">
        <div class="title">
            {{title}}
        </div>
        <div class="close" @click="close">
            ×
        </div>
        <div class="pic">
            <img :src="currentPic"/>
        </div>
        <div class="down" @click="download(currentPic)">
            <img src="@/assets/icons/icon_xiazai.svg"> <span>下载原图</span>
        </div>
        <div class="pic-list" v-show = "picList.length>1">
            <div class="pic-item" v-for="(item, index) in picList" :key="index" @click="changePic(index)">
                <img :src="item">
            </div>
        </div>
    </div>
</template>
<script>
export default{
    name:'PicViewer',
    props:{
        title:{
            type:String,
            default:'图片预览'
        },
        picList:{
            type:Array,
            default:()=>[]
        },
        index:{
            type:Number,
            default:0
        },
        isShow:{
            type:Boolean,
            default:false
        }
    },
    data(){
        return{
            currentPic:this.picList[this.index],
            show:false
        }
    },
    methods:{
        close(){
            this.$emit('close');
        },
        changePic(index){
            this.currentPic = this.picList[index];
        },
        download(uri){
            let link = document.createElement('a')
            link.style.display = 'none'
            link.href = uri.replace("thumbnail","photos")
            link.setAttribute("target", "_blank");
            link.setAttribute("download", name);
            document.body.appendChild(link);
            link.click()
            document.body.removeChild(link);
        }
    },
    watch:{
        picList(){
            if(this.picList.length>1){
                this.currentPic=this.picList[this.index];
            }
            else{
                this.currentPic = this.picList[0]
            }
        },
        isShow(){
            this.show = this.isShow;
        }
    }
}
</script>
<style scoped>
.container{
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.99);
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    z-index: 999;
}
.pic{
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}
.pic img{
    width: 100%;
    object-fit: contain;
}
.close{
    position: absolute;
    top: 20px;
    right: 20px;
    font-size: 2rem;
    color: #fff;
    cursor: pointer;
    border-radius: 50%;
    width: 40px;
    height: 40px;
    border: #fff solid 1px;
    display: flex;
    justify-content: center;
    align-items: center;

}

.pic-list{
    width: 100%;
    position: absolute;
    bottom: 0;
    left: 0;
    height: 100px;
    overflow-x: auto;
    overflow-y: hidden;
    white-space: nowrap;

}
.pic-item{
    width: 100px;
    height: 100px;
    display: inline-block;
    cursor: pointer;
    margin-right: 1px;
}
.pic-item img{
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.title{
    position: absolute;
    max-width: 300px;
    overflow: hidden;
    top: 20px;
    left: 20px;
    font-size: 1.2rem;
    color: #fff;
}
.down{
    border: 1px solid #fff;
    position: absolute;
    bottom: 120px;
    width: 100px;
    display: flex;
    justify-content: center;
    color: #fff;
    padding: 8px 20px;
    border-radius: 50px;
    left: calc(100% / 2 - 75px);
}
.down img{
    width: 24px;
    height: 24px;
}
.down span{
    padding-left: 8px;
}
</style>