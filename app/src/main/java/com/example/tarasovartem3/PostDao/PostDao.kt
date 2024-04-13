package com.example.tarasovartem3.PostDao

import com.example.tarasovartem3.repository.Post


interface PostDao {
    fun getAll(): List<Post>
    fun like(id:Int)
    fun share(id:Int)
    fun save(post: Post): Post
    fun removeById(id: Int)
}
