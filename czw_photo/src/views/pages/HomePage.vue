<template>
    <div class="container" @touchmove="gesture">
        <div class="operation" v-show="deleteShow">
            <button @click="deleteButton">删除</button>
        </div>
        <div class="tip" v-show="picList.length === 0">
            还没有图片哦
        </div>
        <div class="pic-list">
            <div class="pic-item" v-for="(item, index) in picList" :key="index">
                <div class="category-name">
                    {{ item.date }}
                    <!-- <span class="date">{{ item.date }}</span> -->
                </div>
                <div class="items">
                    <div :class="pics_style" v-for="(pic, i) in item.photos" :key="i">
                        <img :src="pic.thumbnail" @click="showPic(i, item)" @touchstart="gtouchstart()"
                            @touchmove="gtouchmove()" @touchend="showDeleteButton()">
                        <label class="cheackbox" v-show="deleteShow" @click="ceeeee">
                            <input type="checkbox" v-model="deleteList" :value="pic.id">
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <PicViewer :title="picViewer.title" :is-show="picViewer.isShow" :pic-list="picViewer.picList"
            :index="picViewer.index" @close="closePicViewer" />

        <div class="deleteDialog" v-show="deleteDialog">
            <div class="deleteDialog-con">
                <div class="deleteDialog-title">删除图片</div>
                <div class="deleteDialog-content">确定删除该这些吗？此操作不可撤回</div>
                <div class="deleteDialog-btn">
                    <div class="cancel" @click="deleteDialogCancel">取消</div>
                    <div class="confirm" @click="deleteDialogConfirm">确定</div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import PicViewer from './components/PicViewer.vue';
import { GetPhotos, DeletePhoto } from '@/api/photos'
export default {
    name: 'HomePage',
    components: {
        PicViewer
    },
    data() {
        return {
            picList: [],
            distance: 0,
            zoomTimer: null,
            pics_style: 'pics_1',
            picViewer: {},
            deleteShow: false,
            deleteList: [],
            deleteDialog: false,
        }
    },

    methods: {
        gesture(e) {
            if (e.touches.length == 2) {
                // 两个手指之间的距离
                let distance = Math.sqrt(Math.pow(e.touches[0].pageX - e.touches[1].pageX, 2) + Math.pow(e.touches[0].pageY - e.touches[1].pageY, 2));
                // 判断是否是第一次触发
                if (this.distance == 0) {
                    this.distance = distance;
                } else {
                    // 两次距离的差值
                    let diff = distance - this.distance;
                    // 两次距离的差值大于5，说明是放大或缩小
                    if (Math.abs(diff) > 5) {
                        // 放大
                        if (diff > 0) {
                            this.zoom('in');
                        } else {
                            // 缩小
                            this.zoom('out');
                        }
                        // 重置距离
                        this.distance = distance;
                    }
                }
            }
        },
        // 执行放大或缩小
        zoom(type) {

            // 0.5s内本方法只能执行一次
            if (this.zoomTimer) {
                return;
            }
            this.zoomTimer = setTimeout(() => {

                if (type == 'in') {
                    this.distance = 0;
                    this.pics_style = 'pics_1'
                } else {
                    this.distance = 0;
                    this.pics_style = 'pics_2'
                }
                this.zoomTimer = null;
            }, 500);
        },
        // 查看图片
        showPic(index, item) {
            let list = []
            item.photos.forEach((item) => {
                list.push(item.thumbnail)
            })
            this.picViewer.isShow = true
            this.picViewer.picList = list
            this.picViewer.title =  "上传日期:"+item.date ;
            this.picViewer.index = index;
        },
        // 关闭图片查看器
        closePicViewer() {
            this.picViewer.isShow = false;
        },
        getAllPic() {
            GetPhotos().then(res => {
                let temp = []
                res.dataSet.forEach(item => {
                    // 获取今天的日期以YYYY-MM-DD的格式
                    let today = new Date().toLocaleDateString().replace(/\//g, '-');
                    let yesterday = new Date(new Date().getTime() - 24 * 60 * 60 * 1000).toLocaleDateString().replace(/\//g, '-');
                    if (item.date == today) {
                        item.date = '今天'
                    }
                    if (item.date == yesterday) {
                        item.date = '昨天'
                    }
                    temp.push(item)

                })
                // 将temp数组赋值给picList
                this.picList = temp
            })
        },

        //长按事件
        gtouchstart() {
            var self = this;
            this.timeOutEvent = setTimeout(function () {
                self.longPress();
            }, 500); //这里设置定时器，定义长按500毫秒触发长按事件
            return false;
        },
        //手释放，如果在500毫秒内就释放，则取消长按事件
        showDeleteButton() {
            clearTimeout(this.timeOutEvent); //清除定时器
            return false;
        },
        //如果手指有移动，则取消所有事件
        gtouchmove() {
            clearTimeout(this.timeOutEvent); //清除定时器
            this.timeOutEvent = 0;
        },
        //长按后响应的事件
        longPress() {
            this.timeOutEvent = 0;
            this.deleteShow = true;
        },
        deleteDialogCancel() {
            this.deleteDialog = false;
        },
        deleteDialogConfirm() {
            DeletePhoto(this.deleteList).then(res => {
                if (res.code == 200) {
                    this.$message({
                        message: '删除成功',
                        type: 'success'
                    });
                    this.getAllPic();
                    this.deleteDialog = false;
                    this.deleteShow = false;
                }
            },()=>{
                this.$message({
                    message: '删除失败',
                    type: 'error'
                });
            })
        },
        deleteButton() {
            if (this.deleteList.length == 0) {
                this.$message({
                    message: '请选择要删除的图片',
                    type: 'warning'
                });
                return;
            }
            this.deleteDialog = true;
        }

    },
    created() {
        this.getAllPic()
    }
}
</script>
<style scoped>
.tip{
    text-align: center;
    color: #999;
    font-size: 16px;
    margin-top: 20px;
}
.date {
    font-size: 14px;
    padding-left: 4px;
    color: rgb(99, 99, 99);
}

.items {
    display: flex;
    width: 100%;
    flex-wrap: wrap;
    position: relative;
}

.pics_1 {
    width: 33.333%;
    height: 150px;
    transition-duration: .5s;
    position: relative;
}

.pics_2 {
    width: 20%;
    height: 90px;
    transition-duration: .5s;
    position: relative;
}

.pics_1 img,
.pics_2 img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition-duration: .5s;
}

.category-name {
    font-size: 20px;
    font-weight: bold;
    padding: 10px;
    text-align: left;
}

.cheackbox {
    position: absolute;
    top: 2px;
    left: 2px;
    width: 100%;
    height: 100%;
    z-index: 999;
    display: block;
}

.cheackbox input {
    position: absolute;
    top: 2px;
    left: 2px;
    width: 20px;
    height: 20px;
    outline: none;
    border-radius: 50%;
    border: none;
}

.operation {
    display: block;
    text-align: right;
    padding: 6px;
    padding-right: 20px;
    position: fixed;
    right: 0;
    box-sizing: border-box;
    z-index: 999;
}

.operation button {
    width: 80px;
    height: 30px;
    border: none;
    border-radius: 6px;
    outline: none;
    color: #FFF;
    background: rgb(255, 70, 70);
    font-size: 14px;
    margin-left: 10px;
}

.deleteDialog {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, .5);
    z-index: 999;
    display: flex;
    justify-content: center;
    align-items: center;
}

.deleteDialog-con {
    width: 300px;
    height: 200px;
    background: #fff;
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    /* align-items: center; */
}

.deleteDialog-title {
    font-size: 1.2rem;
    color: #333;
    text-align: left;
    padding-left: 10px;
}

.deleteDialog-content {
    font-size: 1rem;
    color: #666;
    text-align: center;
    padding-left: 10px;
}

.deleteDialog-btn {
    display: flex;
    justify-content: flex-end;
    align-items: center;
}

.cancel {
    width: 80px;
    height: 30px;
    background: #fff;
    border-radius: 5px;
    color: #666;
    font-size: 1rem;
    text-align: center;
    line-height: 30px;
    margin-right: 10px;
}

.confirm {
    width: 80px;
    height: 30px;
    background: rgb(255, 58, 58);
    border-radius: 5px;
    color: #fff;
    font-size: 1rem;
    text-align: center;
    line-height: 30px;
    margin-right: 10px;
}
</style>