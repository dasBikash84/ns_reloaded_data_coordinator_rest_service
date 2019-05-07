package com.dasbikash.news_server_spring_mvc_rest_datasource.utills


import com.dasbikash.news_server_spring_mvc_rest_datasource.model.EmailAuth
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.EmailTargets
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.io.InputStreamReader
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine
import java.io.BufferedReader
import org.springframework.core.io.ClassPathResource
import java.io.IOException



object EmailUtils {
    val emailAuth: EmailAuth
    val emailTargets: EmailTargets

    init {
        val authReader = InputStreamReader(javaClass.getResourceAsStream("/email_details_auth.json"))
        emailAuth = Gson().fromJson(authReader, EmailAuth::class.java)

        val targetReader = InputStreamReader(javaClass.getResourceAsStream("/email_details_targets.json"))
        emailTargets = Gson().fromJson(targetReader, EmailTargets::class.java)
    }

    fun sendEmail(subject:String,body:String):Boolean{

        val prop = Properties()

        emailAuth.properties!!.keys.asSequence().forEach {
            prop.put(it, emailAuth.properties!!.get(it)!!)
        }

        val session = Session.getInstance(prop,
                object : javax.mail.Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(emailAuth.userName, emailAuth.passWord)
                    }
                })

        try {
            val message = MimeMessage(session)

            message.setFrom(InternetAddress(emailAuth.userName))
            setEmailRecipients(message)
            message.subject = subject
            message.setText(body)

            Transport.send(message)
            return true
        } catch (e: MessagingException) {
            e.printStackTrace()
            return false
        }
    }

    private fun setEmailRecipients(message: MimeMessage) {
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(getToAddressString())
        )

        getCcAddressString()?.let {
            message.setRecipients(
                    Message.RecipientType.CC,
                    InternetAddress.parse(it)
            )
        }

        getBccAddressString()?.let {
            message.setRecipients(
                    Message.RecipientType.BCC,
                    InternetAddress.parse(it)
            )
        }
    }

    private fun getToAddressString():String{
        return emailTargets.toAddresses!!
    }

    private fun getCcAddressString():String?{
        return emailTargets.ccAddresses
    }

    private fun getBccAddressString():String?{
        return emailTargets.bccAddresses
    }
}

