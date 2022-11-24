<template>
    <div class="container">
        <div class="top-bar">
            <div class="back" @click="$router.push({ name: 'Photos' })">
                <img src="@/assets/icons/icon_back.svg">
            </div>
            <div class="title">上传图片</div>
            <div class="back"></div>
        </div>
        <div class="uploadForm">
            <!-- 选择分类 -->
            <div class="category">
                <div class="item_name">选择分类</div>
                <div class="item_input">
                    <select v-model="category_id">
                        <option v-for="category in categories" :value="category.id" :key="category.id">{{ category.name
                        }}</option>
                    </select>
                </div>
            </div>
            <!-- 选择图片 -->
            <div class="pictures">
                <div class="item_name">选择图片<br>({{pic_files.length}}/9)</div>
                <div class="pic_items">
                    <div class="pic_item" v-for="(pic, index) in pic_files" :key="index">
                        <div class="remove_pic" @click="remove(index)">×</div>
                        <img :src="pic.pic_url">
                    </div>
                    <input type="file" id="file_add" style="display: none;">
                    <div class="pic" @click="addFile" v-show="pic_files.length < 9">
                        <img src="@/assets/add.png">
                    </div>
                </div>
            </div>
            <div class="begin_upload">
                <button @click="upload">开始上传</button>
            </div>
        </div>
    </div>
</template>
<script>
import { GetCategoryList } from "@/api/category";
import {UploadPhoto} from "@/api/photos";
export default {
    name: 'UpLoadFilePage',
    data() {
        return {
            filename: '',
            description: '',
            category: '',
            tags: '',
            file: '',
            category_id: 1,
            categories: [],
            pic_files: [],
            address:'123'
        }
    },
    methods:{
        addFile(){
            let file = document.getElementById('file_add');
            file.accept = "image/jpeg,image/png";
            file.click();
            file.onchange = () => {
                let choose_file = document.getElementById('file_add').files[0];
                let reader = new FileReader();
                reader.readAsDataURL(choose_file);
                reader.onload = () => {
                    this.pic_files.push({
                        index: this.pic_files.length+1,
                        pic_url: reader.result,
                        file:choose_file
                    })
                }
            }
        },
        remove(i){
            this.pic_files.splice(i,1)
        },
        getCategories() {
            let userName = this.$store.getters.userInfo.username;
            GetCategoryList(userName).then(res => {
                res.dataSet.forEach(element => {
                    this.categories.push({
                        id: element.id,
                        name: element.categoryName
                    })
                });
                this.category_id = this.categories[0].id;
            })
        },
        async getAddress(){
            let gaodeApi = 'https://restapi.amap.com/v3/ip?key=b7947b0ac215b4ae80a9eaf775b81011';
            await fetch(gaodeApi).then(res => {
                return res.json();
            }).then(res => {
                this.address = res.province + ' ' + res.city;
            })
            
        },
        upload(){
            // 表单验证
            if(this.pic_files.length === 0){
                this.$message({
                    message: '请先选择图片',
                    type: 'warning'
                });
                return;
            }
            // 上传图片
            // MultiPartFile数组
            let formData = new FormData();
            this.pic_files.forEach(element => {
                formData.append('files', element.file);
            });
            let userName = this.$store.getters.userInfo.username;
            let address = this.address;
            let category_id = this.category_id;
            UploadPhoto(category_id,address,userName,formData).then(res => {
                if(res.code === 200){
                    this.$message({
                        message: '上传成功',
                        type: 'success'
                    });
                    this.$router.push({ name: 'Photos' });
                }
            },err=>{
                let {message} = err.response.data;
                this.$message({
                    message: message,
                    type: 'error'
                });
            })
        }
    },
    created(){
        this.getCategories();
        this.getAddress();
    }

}
</script>
<style scoped>
.container {
    width: 100%;
    height: 100%;
    position: fixed;
}
.item_name{
    height: 30px;
    display: flex;
    align-items: center;
    font-size: 14px;
    color: #333;
}

.top-bar {
    width: 100%;
    height: 52px;
    background-color: #FFF;
    display: flex;
    justify-content: center;
    align-items: center;
}

.back {
    width: 60px;
}

.back img {
    width: 20px;
    height: 20px;
    margin-left: 2px;
}

.title {
    width: 100%;
}

.category {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: #FFF;
    border-radius: 10px;
    margin: 10px;
    padding: 10px;
    box-sizing: border-box;
    box-shadow: #a13cff1a 0px 0px 10px;
}

.pictures {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    background: #FFF;
    border-radius: 10px;
    margin: 10px;
    padding: 10px;
    box-sizing: border-box;
    box-shadow: #a13cff20 0px 0px 10px;
}

.item_input {
    width: 60%;
    display: flex;
    justify-content: flex-end;
    align-items: center;
}

.item_input select {
    width: 80%;
    height: 30px;
    border: none;
    outline: none;
    border-bottom: 1px solid rgb(204, 204, 204);
    background: #FFF;
}
.item_input select:focus{
    border-bottom: 1px solid rgb(136, 41, 238);
}

.pic,
.pic_item {
    width: 30%;
    height: 82px;
    border-radius: 10px;
    margin: 4px;
    position: relative;
}

.pic img,
.pic_item img {
    width: 100%;
    height: 100%;
    border-radius: 10px;
    object-fit: cover;
    border: 1px solid rgba(118, 23, 191, 0.726);
}

.pic_item .remove_pic {
    width: 20px;
    height: 20px;
    border-radius: 50%;
    background: #ccc;
    color: #FFF;
    text-align: center;
    line-height: 20px;
    position: absolute;
    top: -6px;
    right: -6px;
}
.pic_items{
    width: 80%;
    display: flex;
    flex-wrap: wrap;
}

.begin_upload{
    width: 100%;
    height: 100px;
    position: absolute;
    bottom: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
}
button{
    width: 80%;
    height: 50px;
    border-radius: 40px;
    background-image: linear-gradient(65deg,  #a13cff, rgb(137, 0, 206));
    color: #fff;
    font-size: 18px;
    border: none;
    outline: none;
}

</style>