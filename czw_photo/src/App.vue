<template>
  <RouterView />
</template>

<script>

export default {
  name: 'App',
  components: {
  },
  mounted() {
  },
  created() {
    //在页面加载时读取sessionStorage里的状态信息
    if (sessionStorage.getItem("store")) {
      let store = sessionStorage.getItem("store");
      this.$store.replaceState(Object.assign({}, this.$store.state, JSON.parse(store == null ? '' : store)))
    }

    //在页面刷新时将vuex里的信息保存到sessionStorage里
    window.addEventListener("beforeunload", () => {
      let state = JSON.stringify(this.$store.state)
      sessionStorage.setItem("store", state == null ? '' : state)
    })

  }
}
</script>

<style>
#app {
  transform: scale(1);
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  width: 368px;
  border-radius: 12px;
  box-shadow: 0 0 20px 1px #bdbfc1;
  height: 96vh;
  margin: 0 auto;
  min-width: 340px;
  margin-top: 2vh;
  -webkit-touch-callout: none !important;
  -webkit-user-select: none;
}


@media screen and (max-width: 768px) {
  #app {
    border: none;
    box-sizing: border-box;
    width: 100%;
    height: 100vh;
    border-radius: 0;
    box-shadow: none;
    margin-top: 0;
    transform: none;
  }


}
</style>
