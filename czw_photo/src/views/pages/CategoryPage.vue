<template>
    
    <PicViewer :title="picViewer.title" :is-show="picViewer.isShow" :pic-list="picViewer.picList" :index="picViewer.index" @close="closePicViewer"></PicViewer>
    <div class="container">
        <div class="topbar">
            <div class="topbar__title">
                <h1>{{title}}</h1>
            </div>
        </div>
        <div class="context" v-show="category_id !== undefined">
            <div class="picturs">
                <div class="pic_item" v-for="item in photo_list" :key="item.id"  @click="showPic(item)">
                    <img :src="item.thumbnail">
                </div>
            </div>
        </div>
        <div class="context" v-show="category_id === undefined">
            <div class="picturs">
                <div class="pic_item" v-for="item in photo_list" :key="item.id" @click="showPic(item)">
                    <img :src="item.thumbnail">
                </div>

                <div class="pic_more" @click="pushMoreToPic">
                    加载更多
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import { GetPhotosByRange, GetAllPhotosByRange,GetCategoryNameById } from '@/api/photos'
import PicViewer from './components/PicViewer.vue';
export default {
    name: 'CategoryPage',
    components:{
        PicViewer
    },
    data() {
        return {
            category_id: undefined,
            photo_list: [],
            bottom: 0,
            picViewer:{
                title:'',
                isShow:false,
                picList:[],
                index:0
            },
            title:"全部图片"
        }
    },
    methods: {
        closePicViewer() {
            this.picViewer.isShow = false;
        },
        getPhotos(num) {
            let start = this.photo_list.length
            let end = num + start
            this.category_id = this.$route.query.id
            GetPhotosByRange(start, end, this.category_id).then(res => {
                if (res.code === 200) {
                    if (res.dataSet.length === 0) {
                        this.$message({
                            message: '没有更多图片了',
                            type:"warning"
                        })
                    } else {
                        this.photo_list = this.photo_list.concat(res.dataSet)
                    }
                }
            })
            GetCategoryNameById(this.category_id).then(res=>{
                if(res.code === 200){
                    this.title = res.dataSet
                }
            })
        },
        showPic(item) {
            this.picViewer.isShow = true;
            this.picViewer.picList = [item.thumbnail];
            this.picViewer.title = "上传地点："+item.address
            this.picViewer.index = 1;
        },

        getAllPhotos(num) {
            let start = this.photo_list.length
            let end = num + start
            GetAllPhotosByRange(start, end).then(res => {
                if (res.code === 200) {
                    if (res.dataSet.length === 0) {
                        this.$message({
                            message: '没有更多图片了',
                            type:"warning"
                        })
                    } else {
                        this.photo_list = this.photo_list.concat(res.dataSet)
                    }
                }
            })

        },
        pushMoreToPic() {
            if (this.category_id === undefined) {
                this.getAllPhotos(10)
            } else {
                this.getPhotos(10)
            }
        },
    },
    created() {
    },
    mounted() {
        this.category_id = this.$route.query.id
        if (this.category_id === undefined) {
            this.getAllPhotos(10)
        } else {
            this.getPhotos(10)
        }
    },

}
</script>
<style scoped>
.topbar{
    height: 60px;
}
.container{
    width: 100%;
    height: 100%;
    position: absolute;
}
.context{
    height: calc(100% - 60px);
    width: 100%;

    overflow: scroll;
}
.pic_item {
    width: 100%;
    height: 160px;
    position: relative;
}

.pic_item img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.download{
    position: absolute;
    z-index: 2;
    bottom: 0;
    height: 50px;
    display: flex;
    align-items: flex-end;
    padding: 6px;
    width: 100%;
    color: rgb(214, 214, 214);
    background: linear-gradient(180deg,rgba(0, 0, 0, 0),rgba(0, 0, 0, 0.9));
}

.pic_more {
    width: 100%;
    height: 50px;
    line-height: 50px;
    text-align: center;
    color: #999;
}
.topbar__title{
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0 20px;
}
</style>