<template>
  <ContentField style="width: 70%">
    <div class="user-count">好友的数量为：{{ count }}</div>
    <table class="table">
      <tbody>
        <tr v-for="user in users" :key="user.id" @click="open_user_profile(user.id)">
          <a class="card" href="#" style="text-decoration: none">
            <div class="card-body" style="padding: 4px">
              <img :src="user.photo" alt="" class="user-photo" />
              <span class="user-username">{{ user.username }}</span>
              <span class="user-sign">{{ user.sign }}</span>
            </div>
          </a>
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
import $ from "jquery";
import { useStore } from "vuex";
import { ref } from "vue";
import router from "@/router/index";

export default {
  components: {
    ContentField,
  },
  setup() {
    const count = ref(0);
    let users = ref([]);
    let current_page = 1;
    let pages = ref([]);
    const store = useStore();

    const click_page = (page) => {
      if (page === -2) page = current_page - 1;
      else if (page === -1) page = current_page + 1;
      let max_pages = parseInt(Math.ceil(count.value / 10));
      if (page >= 1 && page <= max_pages) {
        pull_page(page);
      }
    };

    const update_pages = () => {
      let max_pages = parseInt(Math.ceil(count.value / 10));
      let new_pages = [];
      for (let i = current_page - 1; i < current_page + 2; i++) {
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
        url: "http://localhost:3000/user/list/",
        type: "get",
        data: {
          page,
        },
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          count.value = resp.users_count;
          users.value = resp.users;
          update_pages();
        },
        error(resp) {
          console.log(resp);
        },
      });
    };
    pull_page(current_page);

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

    return {
      count,
      users,
      click_page,
      pages,
      open_user_profile,
    };
  },
};
</script>

<style scoped>
.user-count {
  margin-bottom: 4vh;
  color: #29878f;
  font-weight: 600;
}
.user-photo {
  width: 10vh;
  border-radius: 50%;
  margin-left: 10px;
}
.user-username {
  font-size: large;
  margin-left: 3vh;
  font-weight: bold;
}
.user-sign {
  float: right;
  padding-top: 5vh;
  color: gray;
  padding-right: 2vh;
}
</style>
