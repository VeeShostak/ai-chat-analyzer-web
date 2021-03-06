package com.github.veeshostak.aiChatAnalyzerWeb.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.veeshostak.aiChatAnalyzerWeb.entity.ChatPost;
import com.github.veeshostak.aiChatAnalyzerWeb.service.ChatPostService;

@Controller
@RequestMapping("/chat-post")
public class ChatPostController {
	
	// ex: can inject ChatPostServie using Field Injection. @Qualifier("chatPostServiceUsers")
	// @Autowired
	
	private ChatPostService chatPostService; // dependency
	
	// =============================
	
	// constructor injection
	@Autowired
	public ChatPostController(@Qualifier("chatPostServiceUsers") ChatPostService chatPostService) {
		this.chatPostService = chatPostService;
	}
	
	@GetMapping("/list")
	public String listChatPosts(Model theModel) {
		
		// get chatPosts from the customer service
		List<ChatPost> theChatPosts = chatPostService.getChatPosts();
		
		// add the chatPosts to the model
		theModel.addAttribute("chatPosts", theChatPosts);
		
		return "list-chat-posts";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		ChatPost theChatPost = new ChatPost();
		theModel.addAttribute("chatPost", theChatPost); // name value
		
		return "chat-post-form";
	}
	
	@GetMapping("/delete")
	public String deleteChatPost(@RequestParam("chatPostId") int theId) {
		
		chatPostService.deleteChatPost(theId);
		
		return "redirect:/chat-post/list";
	}
	
	
	@PostMapping("/saveChatPost")
	public String saveCustomer(@ModelAttribute("chatPost") ChatPost theChatPost) {
		
		// save the chatPost using service
		chatPostService.saveChatPost(theChatPost);
		
		return "redirect:/chat-post/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("chatPostId") int theId,
									Model theModel) {
		//@param theId obtained from chatPostId param in chat-post-list
		
		// get the chatPost from our service
		ChatPost theChatPost = chatPostService.getChatPost(theId);	
		
		// set chatPost as a model attribute to pre-populate the 
		// form that we will send the user to
		theModel.addAttribute("chatPost", theChatPost);
		
		// send over to the form	
		return "chat-post-form";
	}
	
	
	@PostMapping("/search")
	 public String searchChatPosts(@RequestParam("theSearchName") String theSearchName,
                                   Model theModel) {

		// search chatPost from the service
		List<ChatPost> theChatPosts = chatPostService.searchChatPosts(theSearchName);
		        
		// add the chatPosts to the model
		theModel.addAttribute("chatPosts", theChatPosts);
		
		return "list-chat-posts";
   }


}

