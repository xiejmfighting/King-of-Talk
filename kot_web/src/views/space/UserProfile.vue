<template>
  <div class="container">
    <div class="row">
      <div class="col-3">
        <div class="card" style="margin-top: 20px">
          <div class="card-body">
            <img :src="user.photo" alt="" style="width: 100%" class="user-photo" />
          </div>
        </div>
        <div class="card" style="margin-top: 5px">
          <div class="card-body" style="padding-top: 6px">
            <div class="user-username">{{ user.username }}</div>
            <div class="user-sign">{{ user.sign }}</div>
          </div>
        </div>
      </div>
      <div class="col-9" style="margin-top: 20px">
        <table class="table">
          <tbody>
            <tr v-for="post in posts" :key="post.id">
              <div class="card">
                <div class="card-body" href="#">
                  <span style="font-size: larger; font-weight: bold">{{
                    post.title
                  }}</span>
                  &nbsp;&nbsp;&nbsp;&nbsp;
                  <span class="post-user-username">{{ post.username }}</span>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span style="font-size: small; color: gray"
                    >编辑于{{ post.modifytime }}</span
                  >
                  <span class="float-end" style="font-size: small; color: gray"
                    >创建于{{ post.createtime }}</span
                  >
                  <div>{{ post.description }}</div>
                  <div style="text-indent: 2em">{{ post.content }}</div>
                </div>
              </div>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import $ from "jquery";
import { useStore } from "vuex";
import { useRoute } from "vue-router";

export default {
  setup() {
    const store = useStore();
    const user = ref({});
    const posts = ref([]);
    const route = useRoute();
    const userId = parseInt(route.params.userId);

    // 获取帖子列表
    const refresh_posts = () => {
      $.ajax({
        url: "http://localhost:3000/userprofile/",
        type: "get",
        data: {
          userId: userId,
        },
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          user.value = resp.user;
          posts.value = resp.posts;
        },
      });
    };

    refresh_posts();

    return {
      posts,
      user,
      refresh_posts,
    };
  },
};
</script>

<style scoped>
.user-username {
  text-align: center;
  font-size: 25px;
}
.user-sign {
  text-align: center;
  padding-top: 20px;
  color: gray;
}
.user-photo {
  width: 6vh;
  border-radius: 50%;
}
</style>
