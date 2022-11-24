<template>
    <div class="message" :class="text.type" v-if="isShow">
        <div class="box">
            <img src="@/assets/icons/warning.svg" v-show="text.type === 'warning'">
            <img src="@/assets/icons/info.svg" v-show="text.type === 'default'">
            <img src="@/assets/icons/success.svg" v-show="text.type === 'success'">
            <img src="@/assets/icons/error.svg" v-show="text.type === 'error'">
            <span>{{ text.message }}</span>
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            isShow: true,
            text: {
                message: '',
                type: 'default'
            }
        }
    },
    mounted() {
        setTimeout(() => {
            this.isShow = false
        }, 4000);
    },
    methods: {
        close() {
            this.isShow = false
        }
    }
}
</script>
<style lang="less" scoped>
.message {
    // width: 260px;
    width: 100%;
    height: 50px;
    position: fixed;
    z-index: 999;
    /* show */
    animation: show 4.1s;

    .box {
        width: 260px;
        margin: 0 auto;
        text-align: center;
        line-height: 40px;
        border-radius: 5px;
        background: rgb(253, 249, 255);
        box-shadow: 0 0 10px rgb(198, 160, 255);
        opacity: 0.8;
        display: flex;
        align-items: center;

        img {
            width: 28px;
            height: 28px;
            margin: 0px 10px;
        }
    }
}

.error {
    color: red;
}

.warning {
    color: orange;
}

.success {
    color: green;
}

.default {
    color: gray;
}

@keyframes show {
    0% {
        top: -100px;
    }

    10% {
        top: 50px;
    }

    75% {
        top: 50px;
    }

    100% {
        top: -100px;
    }
}
</style>