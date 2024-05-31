package com.example.springboot;

import com.example.springboot.requests.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
public class HelloController {
	public HashMap<UUID, Group> groups = new HashMap<>();
	private HashMap<UUID, User> users = new HashMap<>();
	@PostMapping("/create")
	public Group createGroup(@RequestBody CreateRequest createRequest) {
		User userReq = new User(createRequest.user);
		Group group = new Group(createRequest.title, userReq);
		if(!group.authUser(createRequest.userUUID)) return null;
		groups.put(group.getGroupUUID(), group);
		return group;
	}
	@PutMapping("/join")
	public Group joinGroup(@RequestBody JoinRequest joinRequest) {
		Group found = groups.get(joinRequest.groupUUID);
		if(!found.authUser(joinRequest.userUUID)) return null;
		User foundUser = users.get(joinRequest.userUUID);
		found.addUser(foundUser);
		foundUser.addGroup(found.getGroupUUID());
		return found;
	}
	@PostMapping("/{groupUUID}/add")
	public TodoItem createTask(@PathVariable UUID groupUUID, @RequestBody AddItemRequest addItemRequest ) {
		Group found = groups.get(groupUUID);
		if(!found.authUser(addItemRequest.userUUID)) return null;
        return found.addItem(addItemRequest.itemName);
	}
	@GetMapping("/{groupUUID}")
	public Group getGroup(@PathVariable UUID groupUUID) {
		return groups.get(groupUUID);
	}
	@GetMapping("/auth")
	public User initializeUser(@RequestParam(name = "username", required = true) String username) {
		// When user comes here we give then a UUID, then they can keep this ID and
		try {
			User newUser = new User(username);
			users.put(newUser.getId(), newUser);
			 return newUser;
		} catch (Exception e) {
			System.err.println("Error unwrapping in auth");
			 return new User("ERROR");
		}
	}
	@DeleteMapping("/remove")
	public String removeItem(@RequestBody RemoveRequest removeRequest) {
		Group found = groups.get(removeRequest.groupUUID);
		if(!found.authUser(removeRequest.userUUID)) return null;
		found.removeItem(removeRequest.itemUUID);
		return "removed item";
	}
	@PutMapping("/leave")
	public String leaveGroup(@RequestBody LeaveRequest leaveRequest) {
		Group found = groups.get(leaveRequest.groupUUID);
		if(!found.authUser(leaveRequest.userUUID)) return null;
		User foundUser = users.get(leaveRequest.userUUID);
		found.removeUser(foundUser);
		foundUser.removeGroup(found);
		return "user left group";
	}
	@PutMapping("/mark")
	public TodoItem markItem(@RequestBody MarkRequest markRequest) {
		Group found = groups.get(markRequest.groupUUID);
		if(!found.authUser(markRequest.userUUID)) return null;
		return found.markItem(markRequest.itemUUID);
	}
	@GetMapping("/{GroupID}/list")
	public HashMap<UUID, TodoItem> getItems(@RequestParam(name = "uuid", required = true) UUID userUUID, @RequestParam UUID groupUUID) {
		Group found;
		try {
			found = groups.get(groupUUID);
		} catch	(Exception e) {
			System.err.println("Group not found");
			return new HashMap<>();
		}
		if(found.authUser(userUUID)) return found.getItems();
		return new HashMap<>();
	}
}
