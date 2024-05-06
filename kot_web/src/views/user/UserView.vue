<template>
  <div class="container">
    <div class="row">
      <div class="col-3">
        <div class="card" style="margin-top: 20px">
          <div class="card-body">
            <img :src="$store.state.user.photo" alt="" style="width: 100%" />
          </div>
        </div>
        <div class="card" style="margin-top: 5px">
          <div class="card-body" style="padding-top: 6px">
            <div class="user-username">{{ updateuser.username }}</div>
            <div class="user-sign">{{ updateuser.sign }}</div>
          </div>
        </div>
        <!-- Button trigger modal -->
        <button
          type="button"
          class="btn btn-success"
          style="width: 100%; margin-top: 5px"
          data-bs-toggle="modal"
          data-bs-target="#update-user"
        >
          编辑个人资料
        </button>

        <!-- Modal -->
        <div
          class="modal fade"
          id="update-user"
          tabindex="-1"
          aria-labelledby="exampleModalLabel"
          aria-hidden="true"
        >
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">编辑个人资料</h1>
                <button
                  type="button"
                  class="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>
              <div class="modal-body">
                <form>
                  <div class="mb-3">
                    <label for="username" class="form-label">
                      <span style="font-weight: bold">昵称</span>
                    </label>
                    <input
                      v-model="updateuser.username"
                      type="text"
                      class="form-control"
                      id="username"
                      :placeholder="$store.state.user.username"
                    />
                  </div>
                  <div class="mb-3">
                    <label for="photo" class="form-label">
                      <span style="font-weight: bold">头像(仅支持输入图像链接)</span>
                    </label>
                    <input
                      v-model="updateuser.photo"
                      type="text"
                      class="form-control"
                      id="photo"
                      :placeholder="$store.state.user.photo"
                    />
                  </div>
                  <div class="mb-3">
                    <label for="password" class="form-label">
                      <span style="font-weight: bold">密码</span>
                    </label>
                    <input
                      v-model="updateuser.password"
                      type="password"
                      class="form-control"
                      id="password"
                      placeholder="请输入你要更改的密码"
                    />
                  </div>
                  <div class="mb-3">
                    <label for="confirmedPassword" class="form-label">
                      <span style="font-weight: bold">确认密码</span>
                    </label>
                    <input
                      v-model="updateuser.confirmedPassword"
                      type="password"
                      class="form-control"
                      id="confirmedPassword"
                      placeholder="请再次输入你要更改的密码"
                    />
                  </div>
                  <div class="mb-3">
                    <label for="sign" class="form-label">
                      <span style="font-weight: bold">个性签名</span>
                    </label>
                    <input
                      v-model="updateuser.sign"
                      type="text"
                      class="form-control"
                      id="sign"
                      :placeholder="$store.state.user.sign"
                    />
                  </div>
                </form>
              </div>
              <div class="modal-footer">
                <div class="error-message" style="color: red">
                  {{ updateuser.error_message }}
                </div>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                  取消
                </button>
                <button
                  type="button"
                  class="btn btn-primary"
                  @click="update_user(updateuser)"
                >
                  确认修改
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-9">
        <div class="card" style="margin-top: 20px">
          <div class="card-header">
            <span style="font-size: 130%">我的帖子</span>
            <button
              type="button"
              class="btn btn-primary float-end"
              data-bs-toggle="modal"
              data-bs-target="#add-post-button"
            >
              我要发帖
            </button>

            <!-- Modal -->
            <div
              class="modal fade"
              id="add-post-button"
              tabindex="-1"
              aria-labelledby="exampleModalLabel"
              aria-hidden="true"
            >
              <div class="modal-dialog modal-lg">
                <div class="modal-content">
                  <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">
                      <span style="font-size: large; font-weight: bold">我要发帖</span>
                    </h1>
                    <button
                      type="button"
                      class="btn-close"
                      data-bs-dismiss="modal"
                      aria-label="Close"
                    ></button>
                  </div>
                  <div class="modal-body">
                    <form>
                      <div class="mb-3">
                        <label for="add-post-title" class="form-label"
                          ><span style="font-weight: bold">标题</span></label
                        >
                        <input
                          v-model="postadd.title"
                          type="text"
                          class="form-control"
                          id="add-post-title"
                          placeholder="请输入帖子标题"
                        />
                      </div>
                      <div class="mb-3">
                        <label
                          for="add-post-description"
                          class="form-label"
                          style="font-weight: bold"
                          >简介</label
                        >
                        <textarea
                          v-model="postadd.description"
                          class="form-control"
                          id="add-post-description"
                          rows="3"
                          placeholder="请输入帖子简介"
                        ></textarea>
                      </div>
                      <div class="mb-3">
                        <label
                          for="add-post-content"
                          class="form-label"
                          style="font-weight: bold"
                          >内容</label
                        >
                        <textarea
                          v-model="postadd.content"
                          class="form-control"
                          id="add-post-content"
                          rows="7"
                          placeholder="请输入帖子内容"
                        ></textarea>
                      </div>
                    </form>
                  </div>
                  <div class="modal-footer">
                    <div class="error-message" style="color: red">
                      {{ postadd.error_message }}
                    </div>
                    <button
                      type="button"
                      class="btn btn-secondary"
                      data-bs-dismiss="modal"
                    >
                      取消
                    </button>
                    <button type="button" class="btn btn-primary" @click="add_post">
                      发布
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="card-body">
            <table class="table table-striped table-hover">
              <thead>
                <tr>
                  <th>帖子标题</th>
                  <th>创建时间</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="post in posts" :key="post.id">
                  <td>{{ post.title }}</td>
                  <td>{{ post.createtime }}</td>
                  <td>
                    <button
                      type="button"
                      class="btn btn-secondary"
                      style="margin-right: 10px"
                      data-bs-toggle="modal"
                      :data-bs-target="'#update_post_modal-' + post.id"
                    >
                      修改
                    </button>
                    <button
                      type="button"
                      class="btn btn-danger"
                      @click="delete_post(post)"
                    >
                      删除
                    </button>

                    <!-- Modal -->
                    <div
                      class="modal fade"
                      :id="'update_post_modal-' + post.id"
                      tabindex="-1"
                      aria-labelledby="exampleModalLabel"
                      aria-hidden="true"
                    >
                      <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h1 class="modal-title fs-5">
                              <span style="font-size: large; font-weight: bold"
                                >我要编辑帖子</span
                              >
                            </h1>
                            <button
                              type="button"
                              class="btn-close"
                              data-bs-dismiss="modal"
                              aria-label="Close"
                            ></button>
                          </div>
                          <div class="modal-body">
                            <form>
                              <div class="mb-3">
                                <label for="add-post-title" class="form-label"
                                  ><span style="font-weight: bold">标题</span></label
                                >
                                <input
                                  v-model="post.title"
                                  type="text"
                                  class="form-control"
                                  id="add-post-title"
                                  placeholder="请输入帖子标题"
                                />
                              </div>
                              <div class="mb-3">
                                <label
                                  for="add-post-description"
                                  class="form-label"
                                  style="font-weight: bold"
                                  >简介</label
                                >
                                <textarea
                                  v-model="post.description"
                                  class="form-control"
                                  id="add-post-description"
                                  rows="3"
                                  placeholder="请输入帖子简介"
                                ></textarea>
                              </div>
                              <div class="mb-3">
                                <label
                                  for="add-post-content"
                                  class="form-label"
                                  style="font-weight: bold"
                                  >内容</label
                                >
                                <textarea
                                  v-model="post.content"
                                  class="form-control"
                                  id="add-post-content"
                                  rows="7"
                                  placeholder="请输入帖子内容"
                                ></textarea>
                              </div>
                            </form>
                          </div>
                          <div class="modal-footer">
                            <div class="error-message" style="color: red">
                              {{ post.error_message }}
                            </div>
                            <button
                              type="button"
                              class="btn btn-secondary"
                              data-bs-dismiss="modal"
                            >
                              取消
                            </button>
                            <button
                              type="button"
                              class="btn btn-primary"
                              @click="update_post(post)"
                            >
                              确定修改
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, reactive } from "vue";
import $ from "jquery";
import { useStore } from "vuex";
import { Modal } from "bootstrap/dist/js/bootstrap";

export default {
  setup() {
    const store = useStore();
    let posts = ref([]);

    const updateuser = reactive({
      id: store.state.user.id,
      username: store.state.user.username,
      photo: store.state.user.photo,
      sign: store.state.user.sign,
      password: "",
      confirmedPassword: "",
    });

    const postadd = reactive({
      title: "",
      description: "",
      content: "",
      error_message: "",
    });
    //获取帖子列表
    const refresh_posts = () => {
      $.ajax({
        url: "http://localhost:3000/user/post/getlist/",
        type: "get",
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          posts.value = resp;
        },
      });
    };
    refresh_posts();
    //创建帖子
    const add_post = () => {
      postadd.error_message = "";
      $.ajax({
        url: "http://localhost:3000/user/post/add/",
        type: "post",
        data: {
          title: postadd.title,
          description: postadd.description,
          content: postadd.content,
        },
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          if (resp.error_message === "success") {
            postadd.title = "";
            postadd.description = "";
            postadd.content = "";
            //创建完成后，弹窗消失
            Modal.getInstance("#add-post-button").hide();

            refresh_posts();
          } else {
            postadd.error_message = resp.error_message;
          }
        },
      });
    };
    //删除帖子
    const delete_post = (post) => {
      $.ajax({
        url: "http://localhost:3000/user/post/delete/",
        type: "post",
        data: {
          post_id: post.id,
        },
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          if (resp.error_message === "success") {
            refresh_posts();
          }
        },
      });
    };
    //修改帖子
    const update_post = (post) => {
      postadd.error_message = "";
      $.ajax({
        url: "http://localhost:3000/user/post/update/",
        type: "post",
        data: {
          post_id: post.id,
          title: post.title,
          description: post.description,
          content: post.content,
        },
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          if (resp.error_message === "success") {
            //创建完成后，弹窗消失
            Modal.getInstance("#update_post_modal-" + post.id).hide();

            refresh_posts();
          } else {
            postadd.error_message = resp.error_message;
          }
        },
      });
    };

    const update_user = (updateuser) => {
      updateuser.error_message = "";
      $.ajax({
        url: "http://localhost:3000/user/account/update/",
        type: "post",
        data: {
          user_id: updateuser.id,
          username: updateuser.username,
          photo: updateuser.photo,
          sign: updateuser.sign,
          password: updateuser.password,
          confirmedPassword: updateuser.confirmedPassword,
        },
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          if (resp.error_message === "success") {
            //创建完成后，弹窗消失
            Modal.getInstance("#update-user").hide();
            location.reload();
          } else {
            updateuser.error_message = resp.error_message;
          }
        },
      });
    };

    return {
      posts,
      postadd,
      add_post,
      delete_post,
      update_post,
      update_user,
      updateuser,
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
</style>
