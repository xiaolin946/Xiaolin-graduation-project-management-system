import {createRouter, createWebHistory} from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'Home',
        component: ()=> import('../views/index.vue')
    },
    {
        path: '/Login',
        name: 'Login',
        component: ()=> import('../views/Login.vue')
    },
    {
        path: '/Administrator',
        name: 'Administrator',
        component: ()=> import('../views/Administrator.vue'),
        children: [
            {path: 'main', name: "main", component: ()=> import('../components/Home.vue')},
            {path: 'admin_manage', name: 'admin_manage', component: ()=> import('../components/Administrator_Manage.vue')},
            {path: 'teacher_manage', name: 'teacher_manage', component: ()=> import('../components/Teacher_Manage.vue')},
            {path: 'student_manage', name: 'student_manage', component: ()=> import('../components/Student_Manage.vue')},
            {path: 'admin_kt', name: 'admin_kt', component: ()=> import('../components/admin_kt.vue')},
            {path: 'admin_dt', name: 'admin_dt', component: ()=> import('../components/admin_dt.vue')},
            {path: 'admin_news', name: 'admin_news', component: ()=> import('../components/admin_news.vue')},
            {path: 'myxx', name: 'myxx', component: ()=> import('../components/myxx.vue')},
            {path: 'upsd', name: 'upsd', component: ()=> import('../components/upsd.vue')}
        ]
    },
    {
        path: '/Student',
        name: 'Student',
        component: ()=> import('../views/Student.vue'),
        children: [
            {path: 'main', name: "main2", component: ()=> import('../components/Home.vue')},
            {path: 'student_dt', name: "student_dt", component: ()=> import('../components/student_dt.vue')},
            {path: 'student_news', name: "student_news", component: ()=> import('../components/student_news.vue')},
            {path: 'student_upload', name: "student_upload", component: ()=> import('../components/student_upload.vue')},
            {path: 'myxx', name: "myxx2", component: ()=> import('../components/myxx.vue')},
            {path: 'upsd', name: "upsd2", component: ()=> import('../components/upsd.vue')}
        ]
    },
    {
        path: '/Teacher',
        name: 'Teacher',
        component: ()=> import('../views/Teacher.vue'),
        children: [
            {path: 'main', name: "main3", component: ()=> import('../components/Home.vue')},
            {path: 'teacher_kt', name: "teacher_kt", component: ()=> import('../components/teacher_kt.vue')},
            {path: 'teacher_dt', name: "teacher_dt", component: ()=> import('../components/teacher_dt.vue')},
            {path: 'teacher_news', name: "teacher_news", component: ()=> import('../components/teacher_news.vue')},
            {path: 'teacher_gnews', name: "teacher_gnews", component: ()=> import('../components/teacher_gnews.vue')},
            {path: 'myxx', name: "myxx3", component: ()=> import('../components/myxx.vue')},
            {path: 'upsd', name: "upsd3", component: ()=> import('../components/upsd.vue')}
        ]
    },
]

const router = createRouter({
    history: createWebHistory("/"),
    routes: routes,
})



//输出对象
export default router