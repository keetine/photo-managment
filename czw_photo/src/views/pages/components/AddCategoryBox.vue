<template>
    <div :class="con_style">
        <div class="box">
            <div class="title">添加分类</div>
            <div class="editor">
                <input type="text" v-model="name" placeholder="请输入分类名称">
                <br>
                <img :src="pic_url" @click="choose_file"/>
                <input type="file" id="choose_pic" style="display: none;">
            </div>
            <div class="operation">
                <div class="cancel" @click="cancel">取消</div>
                <div class="confirm" @click="confirm" ref="submit">{{editId === null ? "确定":"更新"}}</div>
            </div>
        </div>
    </div>
</template>
<script>
import {AddCategory, GetCategoryById,UpdateCategoryById} from "@/api/category";
export default {
    name: 'AddCategoryBox',
    props: {
        isShow: {
            type: Boolean,
            default: false
        },
        editId: {
            type: Number,
            default: null
        }
    },
    data() {
        return {
            con_style:'con_hide',
            pic_url:require('@/assets/add_pic.png'),
            name:'',
            file:null
        }
    },
    mounted() {
    },
    methods: {
        cancel() {
            this.$emit('cancel');
        },
        confirm() {
            // 表单验证
            if (this.name === '') {
                this.$message({
                    message: '请输入分类名称',
                    type: 'warning'
                });
                return;
            }
            if (this.pic_url === require('@/assets/add_pic.png') && this.file === null) {
                this.$message({
                    message: '请选择分类图片',
                    type: 'warning'
                });
                return;
            }

            let sub_text = this.$refs.submit.innerText;

            if (sub_text === '确定') {
                this.addCategory();
            } else {
                this.updateCategory();
            }
            this.$emit('confirm');
            
        },
        updateCategory(){
            // 检测file是否有值
            if (this.file === null) {
                this.$message({
                    message: '请选择分类图片',
                    type: 'warning'
                });
                return;
            }
            let formData = new FormData();
            formData.append('file', this.file);
            UpdateCategoryById(this.editId,this.name,formData).then(res=>{
                if (res.code === 200) {
                    this.$message({
                        message: '更新成功',
                        type: 'success'
                    });
                    this.$emit('confirm');
                } else {
                    this.$message({
                        message: '更新失败',
                        type: 'error'
                    });
                }
            })
        },
        addCategory(){
            
            let userName = this.$store.getters.userInfo.username;
            let formData = new FormData();
            formData.append('file', this.file);
            AddCategory(userName, this.name, formData).then(res => {
                if (res.code === 200) {
                    this.$message({
                        message: '添加分类成功',
                        type: 'success'
                    });
                    this.$emit('confirm');
                }

            },err=>{
                let {data} = err.response;
                this.$message({
                    message: data.message,
                    type: 'error'
                });
            })
        },
        choose_file(){
            let file_input = document.getElementById('choose_pic')
            // 只能选择jpg和png格式的图片
            file_input.accept = 'image/jpg'
            file_input.click()
            // 选择图片后，将图片的路径赋值给img标签的src属性
            file_input.onchange = () => {
                this.pic_url = URL.createObjectURL(file_input.files[0])
                this.file = file_input.files[0]
            }

        },
        getCategory(id){
            GetCategoryById(id).then(res => {
                    if (res.code === 200) {
                        this.pic_url = res.dataSet.thumbnail;
                        this.name = res.dataSet.categoryName;
                    }
            },err=>{
                let {data} = err.response;
                this.$message({
                    message: data.message,
                    type: 'error'
                });
            })
        }
    },
    watch: {
        isShow() {
            if (this.isShow) {
                this.con_style = 'container';
            } else {
                this.con_style = 'con_hide';
            }
        },
        editId() {
            if (this.editId === null) {
                this.pic_url = require('@/assets/add_pic.png');
                this.name = '';
            } else {
                this.getCategory(this.editId);
            }
        }
    }
}
</script>
<style scoped>
.container {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 100;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    opacity: 1;
    transition: all 0.5s;
}
.con_hide{
    position: fixed;
    top: -100vh;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 100;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    opacity: 0;
    transition: all 0.5s;
}
.box {
    width: 80%;
    height: 240px;
    background-color: #fff;
    border-radius: 10px;
    position: relative;
}
.title{
    height: 40px;
    line-height: 40px;
    padding-left: 10px;
    text-align: left;
    font-size: 1.2rem;
    border-bottom: 1px solid #eee;
}
.editor{
    margin-top: 10px;
}
.editor input{
    width: 80%;
    height: 40px;
    margin: 0 auto;
    border: none;
    border-bottom: 1px solid rgb(190, 190, 190);
    padding-left: 10px;
    outline: none;
    font-size: 18px;
    transition-duration: .4s;
}
.editor input:focus{
    border-bottom: 1px solid rgb(99, 27, 175);
    transition-duration: .4s;
}
.editor img{
    width: 60%;
    height: 80px;
    margin: 10px;
    border: 1px solid rgb(190, 190, 190);
    border-radius: 5px;
    /**图片铺满 */
    object-fit: cover;
}
.operation{
    position: absolute;
    bottom: 0;
    width: 100%;
    height: 40px;
    display: flex;
    justify-content: flex-end;
    align-items: center;
}
.operation div{
    width: 80px;
    height: 100%;
    line-height: 40px;
    text-align: center;
    font-size: 1rem;
    cursor: pointer;
    margin-bottom: 14px;
    margin-right: 14px;
}
.cancel{
    border-right: 1px solid #eee;
}
.confirm{
    color: rgb(99, 27, 175);
}
</style>