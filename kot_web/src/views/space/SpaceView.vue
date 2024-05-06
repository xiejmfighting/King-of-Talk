<template>
  <ContentField>
    <table class="table">
      <tbody>
        <tr v-for="post in posts" :key="post.post.id">
          <div class="card">
            <div class="card-body" href="#">
              <span style="font-size: larger; font-weight: bold">{{
                post.post.title
              }}</span>
              &nbsp;&nbsp;&nbsp;&nbsp;
              <a
                href="#"
                style="text-decoration: none; color: inherit"
                @click="open_user_profile(post.post.userId)"
              >
                <img :src="post.photo" alt="" class="post-user-photo" />
                &nbsp;
                <span class="post-user-username">{{ post.username }}</span>
              </a>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <span style="font-size: small; color: gray"
                >编辑于{{ post.post.modifytime }}</span
              >

              <span class="float-end" style="font-size: small; color: gray"
                >创建于{{ post.post.createtime }}</span
              >
              <div>{{ post.post.description }}</div>
              <div style="text-indent: 2em">{{ post.post.content }}</div>
            </div>
          </div>
        </tr>
      </tbody>
    </table>
    <nav aria-label="Page navigation example">
      <ul class="pagination" style="float: right">
        <li class="page-item" @click="click_page(-2)">
          <a class="page-link" href="#" aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
          </a>
        </li>
        <li
          :class="'page-item ' + page.is_active"
          v-for="page in pages"
          :key="page.number"
          @click="click_page(page.number)"
        >
          <a class="page-link" href="#">{{ page.number }}</a>
        </li>

        <li class="page-item" @click="click_page(-1)">
          <a class="page-link" href="#" aria-label="Next">
            <span aria-hidden="true">&raquo;</span>
          </a>
        </li>
      </ul>
    </nav>
  </ContentField>
</template>

<script>
import ContentField from "../../components/ContentField.vue";
import { useStore } from "vuex";
import $ from "jquery";
import { ref } from "vue";
import router from "@/router/index";

export default {
  components: {
    ContentField,
  },
  setup() {
    const store = useStore();
    let posts = ref([]);
    let current_page = 1;
    let total_posts = 0;
    let pages = ref([]);

    const click_page = (page) => {
      if (page === -2) page = current_page - 1;
      else if (page === -1) page = current_page + 1;
      let max_pages = parseInt(Math.ceil(total_posts / 5));
      if (page >= 1 && page <= max_pages) {
        pull_page(page);
      }
    };

    const update_pages = () => {
      let max_pages = parseInt(Math.ceil(total_posts / 5));
      let new_pages = [];
      for (let i = current_page - 2; i <= current_page + 2; i++) {
        if (i >= 1 && i <= max_pages) {
          new_pages.push({
            number: i,
            is_active: i === current_page ? "active" : "",
          });
        }
      }
      pages.value = new_pages;
    };

    const pull_page = (page) => {
      current_page = page;
      $.ajax({
        url: "http://localhost:3000/post/getlist/",
        type: "get",
        data: {
          page,
        },
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          console.log(resp);
          posts.value = resp.posts;
          total_posts = resp.posts_count;
          update_pages();
        },
        error(resp) {
          console.log(resp);
        },
      });
    };

    const open_user_profile = (userId) => {
      if (store.state.user.id == userId) {
        router.push({
          name: "user_index",
        });
      } else {
        router.push({
          name: "userprofile",
          params: {
            userId,
          },
        });
      }
    };

    pull_page(current_page);
    return {
      posts,
      pages,
      click_page,
      open_user_profile,
    };
  },
};
</script>

<style scoped>
img.post-user-photo {
  width: 6vh;
  border-radius: 50%;
}
</style>
