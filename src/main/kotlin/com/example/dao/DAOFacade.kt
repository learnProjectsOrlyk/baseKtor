package com.example.dao

import com.example.models.*

interface DAOFacade {
    suspend fun allArticles(): List<Article>
    suspend fun getArticle(id: Int): Article?
    suspend fun addArticle(title: String, body: String): Article?
    suspend fun editArticle(id: Int, title: String, body: String): Boolean
    suspend fun deleteArticle(id: Int): Boolean
}