<template>
<ContentField  v-if="$store.state.pk.status==='playing'">匹配</ContentField>
<MatchGround v-if="$store.state.pk.status==='matching'"></MatchGround>
</template>

<script>
import ContentField from '../../components/ContentField.vue'
import {onMounted,onUnmounted} from 'vue'
import {useStore} from 'vuex'
import MatchGround from '../../components/MatchGround.vue'

export default{
    components:{
        ContentField,MatchGround
    },
    setup(){
        //从前端向后端建立一个WebSocket链接
        const store=useStore();
        const socketUrl=`ws://localhost:3000/websocket/${store.state.user.token}/`;

        let socket=null;
        onMounted(()=>{
            store.commit("updateOpponent",{
                username:"我的聊天对象",
                photo:"https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png"
            })
            socket=new WebSocket(socketUrl);

            socket.onopen=()=>{
                console.log("connected");
                store.commit("updateSocket",socket);
            }
            socket.onmessage=msg=>{
                const data=JSON.parse(msg.data);
                if(data.event==="start-matching"){//匹配成功
                    store.commit("updateOpponent",{
                        username: data.opponent_username,
                        photo:data.opponent_photo,
                    });
                    setTimeout(()=>{
                        store.commit("updateStatus","playing");
                    },2000);
                    
                }
            }
            socket.onclose=()=>{
                console.log("disconnected!");
            }
        });

        onUnmounted(()=>{
            socket.close();
            store.commit("updateStatus","matching");
        })


    }
}

</script>

<style scoped>

</style>