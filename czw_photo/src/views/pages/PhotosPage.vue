<template>
    <div class="container">
        <AddCategoryBox :isShow="add_category.show" :editId="add_category.id" @cancel="handleCateCancel" @confirm="handleCateConfirm"/>
        <div class="mian">
            <div class="category-list">
                <div class="category-item">
                    <div class="category-item-img">
                        <img src="@/assets/templates/image.jpg">
                    </div>
                    <div class="category-item-name"  @click="watchCategory()">
                        <span>{{ all.categoryName }}</span>
                        <span>{{ all.photoNum}}</span>
                    </div>
                </div>
                <!---->
                <div class="category-item" v-for="item,index in categoryList" :key="item.id">
                    <div class="category-item-img">
                        <img :src="item.thumbnail">
                    </div>
                    <div class="category-item-name"  @click="watchCategory(item.id)">
                        <span>{{ item.categoryName }}</span>
                        <span>{{ item.photoNum }}</span>
                    </div>
                    <div class="more" @click="showMoreMenu(index)">
                        <img src="@/assets/icons/more.svg">
                        <div :class="{menu_show:showmore === index,menu:showmore !== index}">
                            <div class="menu-item" @click="handleCateEdit(item.id)">
                                <img src="@/assets/icons/edit.svg">
                                <span>编辑</span>
                            </div>
                            <div class="menu-item" @click="handleCateDelete(item.id)">
                                <img src="@/assets/icons/delete.svg">
                                <span>删除</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="add">
            <div class="add-btn" @click="add">
                <img src="@/assets/icons/add.svg">
            </div>
            <div :class="add_menu">
                <div class="addmenu-item" @click="addCategory">
                    <img src="@/assets/icons/classfy.svg" alt="add">
                    <span>添加分类</span>
                </div>
                <div class="addmenu-item" @click="addPhoto">
                    <img src="@/assets/icons/picture.svg" alt="add">
                    <span>添加照片</span>
                </div>
            </div>
        </div>
        <div class="deleteDialog" v-show="deleteDialog">
            <div class="deleteDialog-con">
                <div class="deleteDialog-title">删除分类</div>
                <div class="deleteDialog-content">确定删除该分类吗？此操作不会删除该分类下的图片</div>
                <div class="deleteDialog-btn">
                    <div class="cancel" @click="deleteDialogCancel">取消</div>
                    <div class="confirm" @click="deleteDialogConfirm(deleteId)">确定</div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import AddCategoryBox from './components/AddCategoryBox.vue';
import {GetCategoryList,DeleteCategoryById} from "@/api/category";
import {GetPhotoCount} from "@/api/photos";
export default {
    name: 'PhotosPage',
    components: {
        AddCategoryBox
    },
    data() {
        return {
            categoryList: [],
            add_menu: "addmenu_hide",
            add_category: {
                show: false,
                id: null,
            },
            showmore: false,
            deleteDialog: false,
            deleteId: null,
            all:{
                categoryName:'全部',
                photoNum:0
            }
        }
    },
    methods: {
        add() {
            if (this.add_menu == "addmenu_hide") {
                this.add_menu = "addmenu";
            } else {
                this.add_menu = "addmenu_hide";
            }
        },
        goUpload() {
            this.$router.push({ name: 'Upload' })
        },
        watchCategory(query) {
            this.$router.push({name:'Category',query:{id:query}})
        },
        addCategory() {
            this.add_menu = "addmenu_hide";
            this.add_category.show = true;
            this.add_category.id = null;
        },
        addPhoto() {
            console.log('addPhoto');
            this.add_menu = "addmenu_hide";
            this.goUpload();
        },
        handleCateCancel() {
            this.add_category.show = false;
        },
        handleCateConfirm() {
            this.add_category.show = false;
            this.getCategories();
        },
        handleCateEdit(id) {
            this.add_category.id = id;
            this.add_category.show = true;
        },
        handleCateDelete(id) {
            this.deleteId = id;
            this.deleteDialog = true;
        },
        
        getCategories() {
            let userName = this.$store.getters.userInfo.username;
            GetCategoryList(userName).then(res => {
                this.categoryList = res.dataSet;
            })
            GetPhotoCount().then(res => {
                this.all.categoryName = '全部';
                this.all.photoNum = res.dataSet;
            })
        },
        showMoreMenu(i){
            if(this.showmore===i){
                this.showmore=false;
            }else{
                this.showmore=i;
            }
        },
        deleteDialogCancel(){
            this.deleteDialog = false;
        },
        deleteDialogConfirm(id){
            this.deleteDialog = false;
            DeleteCategoryById(id).then(res => {
                if(res.code === 200){
                    this.$message({
                    message: '删除成功',
                    type: 'success'
                });
                this.getCategories();
                }
            },err=>{
                let msg = err.response.data.message;
                this.$message({
                    message: msg,
                    type: 'error'
                });
            })
        }
    },
    created(){
        this.getCategories()
    }
}
</script>
<style scoped>
.more{
    position: absolute;
    bottom: 10px;
    left: 10px;
    width: 30px;
    height: 30px;
    z-index: 9;
    color: #fff;
    border-radius: 50%;
    background: rgba(255, 255, 255, 0.5);
    padding: 4px;

    box-sizing: border-box;
}
.more img:active{   
    transition-duration: .5s;
    transform: scale(.6);
}
.menu{
    position: absolute;
    top: 0px;
    left: 20px;
    width: 90px;
    height: 0px;
    background: rgba(255, 255, 255, .8);
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
    overflow: hidden;
    transition-duration: .3s;
    opacity: .3;
}
.menu_show{
    position: absolute;
    top: -70px;
    left: 20px;
    width: 90px;
    height: 73px;
    background: rgba(255, 255, 255, .8);
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
    overflow: hidden;
    transition-duration: .3s;
}
.menu-item{
    display: flex;
    color: rgb(91, 91, 91);
    padding: 6px 0;
}
.menu-item:nth-child(1){
    border-bottom: 1px solid rgb(201, 201, 201);
}
.menu-item >img{
    width: 24px;
    height: 24px;
    margin-right: 10px;
}

.more>img{
    width: 100%;
    height: 100%;
}
.add {
    position: fixed;
    bottom: 120px;
    right: 20px;
    width: 40px;
    height: 40px;
    background: #fff;
    border-radius: 50%;
    box-shadow: 0 0 10px #ddd;
    z-index: 999;
}

.add-btn:active {
    transition-duration: .4s;
    transform: scale(.8);
    rotate: 360deg;
}

.add-btn {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    transition-duration: .4s;
}

.add img {
    display: block;
    width: 26px;
    height: 26px;
}

.addmenu {
    width: 120px;
    height: 100px;
    position: absolute;
    bottom: 50px;
    right: 0;
    border-radius: 10px;
    background: #fff;
    overflow: hidden;
    transition-duration: .4s;
}

.addmenu_hide {
    width: 120px;
    height: 0px;
    position: absolute;
    bottom: 50px;
    right: 0;
    border-radius: 10px;
    background: #fff;
    overflow: hidden;
    transition-duration: .4s;
    font-size: 10px;
    opacity: .2;
}

.addmenu-item {
    width: 100%;
    height: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
}
.addmenu-item img{
    width: 20px;
    height: 20px;
    margin-right: 10px;
}
.addmenu-item:nth-child(1) {
    border-bottom: 1px solid #ddd;
}

.category-item {
    width: 100%;
    height: 150px;
    box-sizing: border-box;
    position: relative;
    z-index: 1;
}

.category-item-name {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-end;
    z-index: 2;
    height: 100%;
    width: 100%;
    position: absolute;
    /** 背景从左往右线性渐变 */
    background: linear-gradient(to right, rgba(0, 0, 0, 0) 0%, rgba(0, 0, 0, 0) 50%, rgba(0, 0, 0, .8) 100%);
}

.category-item-name span {
    padding-right: 20px;
    font-size: 1.8rem;
    color: #fff;
}

.category-item-name span:nth-child(1) {
    padding-top: 10px;
    color: #eee;
}

.category-item-name span:nth-child(2) {
    padding-top: 10px;
    font-size: 1.2rem;
    color: #aaa;
}

.category-item-img {
    z-index: 1;
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    overflow: hidden;
}

.category-item-img img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    object-position: center;
}
.deleteDialog{
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
.deleteDialog-con{
    width: 300px;
    height: 200px;
    background: #fff;
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    /* align-items: center; */
}
.deleteDialog-title{
    font-size: 1.2rem;
    color: #333;
    text-align: left;
    padding-left: 10px;
}
.deleteDialog-content{
    font-size: 1rem;
    color: #666;
    text-align: center;
    padding-left: 10px;
}
.deleteDialog-btn{
    display: flex;
    justify-content: flex-end;
    align-items: center;
}
.cancel{
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
.confirm{
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