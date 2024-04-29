<template>
<div class="container">
    <div class="row">
        <div class="col-3">
            <div class="card" style="margin-top: 20px;">
                <div class="card-body">
                    <img :src="$store.state.user.photo" alt="" style="width:100%;">
                </div>
            </div>
        </div>
        <div class="col-9">
            <div class="card" style="margin-top: 20px;">
                <div class="card-header" >
                    <span style="font-size: 130%;">我的帖子</span>
                    <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal" data-bs-target="#add-post-button">
                        我要发帖
                    </button>
                    
                    <!-- Modal -->
                    <div class="modal fade" id="add-post-button" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalLabel"><span style="font-size: large; font-weight:bold">我要发帖</span></h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                            <form>
                                <div class="mb-3">
                                    <label for="add-post-title" class="form-label"><span style="font-weight: bold;">标题</span></label>
                                    <input v-model="postadd.title" type="text" class="form-control" id="add-post-title" placeholder="请输入帖子标题">
                                </div>
                                <div class="mb-3">
                                    <label for="add-post-description" class="form-label" style="font-weight: bold;">简介</label>
                                    <textarea v-model="postadd.description" class="form-control" id="add-post-description" rows="3" placeholder="请输入帖子简介"></textarea>
                                </div>
                                <div class="mb-3">
                                    <label for="add-post-content" class="form-label" style="font-weight: bold;">内容</label>
                                    <textarea v-model="postadd.content" class="form-control" id="add-post-content" rows="7" placeholder="请输入帖子内容"></textarea>
                                </div>
                                
                                </form>
                            </div> 
                            <div class="modal-footer">
                            <div class="error-message" style="color: red;">{{postadd.error_message}}</div>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" @click="add_post">发布</button>
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
                                <td>{{post.title}}</td>
                                <td>{{post.createtime}}</td>
                                <td>
                                    <button type="button" class="btn btn-secondary" style="margin-right: 10px;"   data-bs-toggle="modal" :data-bs-target="'#update_post_modal-'+post.id">修改</button>
                                    <button type="button" class="btn btn-danger" @click="delete_post(post)">删除</button>

                                       <!-- Modal -->
                                    <div class="modal fade" :id="'update_post_modal-'+post.id" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog modal-lg">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                            <h1 class="modal-title fs-5"><span style="font-size: large; font-weight:bold">我要发帖</span></h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                            <form>
                                                <div class="mb-3">
                                                    <label for="add-post-title" class="form-label"><span style="font-weight: bold;">标题</span></label>
                                                    <input v-model="post.title" type="text" class="form-control" id="add-post-title" placeholder="请输入帖子标题">
                                                </div>
                                                <div class="mb-3">
                                                    <label for="add-post-description" class="form-label" style="font-weight: bold;">简介</label>
                                                    <textarea v-model="post.description" class="form-control" id="add-post-description" rows="3" placeholder="请输入帖子简介"></textarea>
                                                </div>
                                                <div class="mb-3">
                                                    <label for="add-post-content" class="form-label" style="font-weight: bold;">内容</label>
                                                    <textarea v-model="post.content" class="form-control" id="add-post-content" rows="7" placeholder="请输入帖子内容"></textarea>
                                                </div>
                                                
                                                </form>
                                            </div> 
                                            <div class="modal-footer">
                                            <div class="error-message" style="color: red;">{{post.error_message}}</div>
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                            <button type="button" class="btn btn-primary" @click="update_post(post)">确定修改</button>
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
import {ref ,reactive} from 'vue'
import $ from 'jquery'
import {useStore} from 'vuex'
import {Modal} from'bootstrap/dist/js/bootstrap'

export default{
    setup(){
        const store=useStore();
        let posts=ref([]);

        const postadd=reactive({
            title:"",
            description:"",
            content:"",
            error_message:"",
        });
        //获取帖子列表
        const refresh_posts=()=>{
            $.ajax({
                url:"http://localhost:3000/user/post/getlist/",
                type:"get",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                success(resp){
                    posts.value=resp;
                }
            })
        }
        refresh_posts();
        //创建帖子
        const add_post=()=>{
            postadd.error_message="";
            $.ajax({
                url:"http://localhost:3000/user/post/add/",
                type:"post",
                data:{
                    title:postadd.title,
                    description:postadd.description,
                    content:postadd.content,
                },
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                success(resp){
                    if(resp.error_message==="success"){
                        postadd.title="";
                        postadd.description="";
                        postadd.content="";
                        //创建完成后，弹窗消失
                        Modal.getInstance("#add-post-button").hide();

                        refresh_posts();
                    }else{
                        postadd.error_message=resp.error_message;
                    }
                }
            })
        }
        //删除帖子
        const delete_post=(post)=>{
            $.ajax({
                url:"http://localhost:3000/user/post/delete/",
                type:"post",
                data:{
                    post_id:post.id
                },
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                success(resp){
                    if(resp.error_message==="success"){
                        refresh_posts();
                    }
                }
            })
        }
        //修改帖子
        const update_post=(post)=>{
            postadd.error_message="";
            $.ajax({
                url:"http://localhost:3000/user/post/update/",
                type:"post",
                data:{
                    post_id:post.id,
                    title:post.title,
                    description:post.description,
                    content:post.content,
                },
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                success(resp){
                    if(resp.error_message==="success"){
                        
                        //创建完成后，弹窗消失
                        Modal.getInstance('#update_post_modal-'+post.id).hide();

                        refresh_posts();
                    }else{
                        postadd.error_message=resp.error_message;
                    }
                }
            })
        }


        return{
            posts,postadd,add_post,delete_post,update_post
        }
        }
    }

    </script>
    
    <style scoped>
    
    </style>