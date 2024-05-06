<template>
  <div>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container">
        <router-link class="navbar-brand" :to="{ name: 'home' }"
          >King Of Talk</router-link
        >
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarText"
          aria-controls="navbarText"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarText">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <router-link
                :class="route_name == 'pk_index' ? 'navbar-brand' : 'nav-link'"
                aria-current="page"
                :to="{ name: 'pk_index' }"
                >匹配陌生人</router-link
              >
            </li>
            <li class="nav-item">
              <router-link
                :class="route_name == 'userlist_index' ? 'navbar-brand' : 'nav-link'"
                aria-current="page"
                :to="{ name: 'userlist_index' }"
                >好友列表</router-link
              >
            </li>
            <li class="nav-item">
              <router-link
                :class="route_name == 'space_index' ? 'navbar-brand' : 'nav-link'"
                aria-current="page"
                :to="{ name: 'space_index' }"
                >动态空间</router-link
              >
            </li>
            <form class="d-flex" style="margin-left: 5px">
              <input
                v-model="keyword"
                class="form-control me-2"
                type="search"
                placeholder="输入关键词搜索"
                aria-label="Search"
                style="width: 70%"
              />
              <button @click="search" type="button" class="btn btn-primary">搜索</button>
            </form>
          </ul>

          <ul class="navbar-nav" v-if="$store.state.user.token">
            <li class="nav-item dropdown">
              <a
                class="nav-link dropdown-toggle"
                href="#"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                {{ $store.state.user.username }}
              </a>
              <ul class="dropdown-menu">
                <router-link class="dropdown-item" :to="{ name: 'user_index' }"
                  >个人空间</router-link
                >
                <li><hr class="dropdown-divider" /></li>
                <li><a class="dropdown-item" href="#" @click="logout">退出</a></li>
              </ul>
            </li>
          </ul>

          <ul class="navbar-nav" v-else-if="!$store.state.user.pulling_info">
            <li class="nav-item">
              <router-link
                class="nav-link"
                :to="{ name: 'user_account_login' }"
                role="button"
              >
                登录
              </router-link>
            </li>
            <li class="nav-item">
              <router-link
                class="nav-link"
                :to="{ name: 'user_account_register' }"
                role="button"
              >
                注册
              </router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
import { useRouter } from "vue-router";
import { computed } from "vue";
import { useStore } from "vuex";
import { ref } from "vue";

export default {
  setup() {
    const store = useStore();
    const keyword = ref("");
    //取得现在的页面的路径
    const router = useRouter();
    const showEmptyKeywordMessage = ref(false); // 是否显示关键词为空的提示信息
    let route_name = computed(() => router.currentRoute.value.name);

    const logout = () => {
      store.dispatch("logout");
    };

    const search = async () => {
      if (!keyword.value.trim()) {
        // 如果关键词为空，则显示提示信息，并返回
        showEmptyKeywordMessage.value = true;
        return;
      } else {
        // 如果关键词不为空，则隐藏提示信息
        showEmptyKeywordMessage.value = false;
        router.push({ name: "search_content", params: { keyword: keyword.value } });
      }
    };

    return {
      route_name,
      logout,
      keyword,
      search,
      showEmptyKeywordMessage,
    };
  },
};
</script>

<style scoped></style>
