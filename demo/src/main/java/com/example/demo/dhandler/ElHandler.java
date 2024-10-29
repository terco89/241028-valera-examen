package com.example.demo.dhandler;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Service
public class ElHandler extends TextWebSocketHandler{
	ArrayList<String> mensajes = new ArrayList<String>();
	private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<WebSocketSession>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception{
		sessions.add(session);
		String lista = "";
		for(int i = 0; i < mensajes.size();i++) {
			lista += mensajes.get(i)+"<br>";
		}
		TextMessage texto = new TextMessage(lista);
		session.sendMessage(texto);
	}
	
	
	public void afterConnectionClosed(WebSocketSession session) throws Exception{
		sessions.remove(session);
	}
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
		String mi = "websockect";
		if(message.getPayload().contains(mi)) {
			String prueba = "WebSocket es un protocolo del tipo TCP, para el transporte de datos en tiempo real y bidireccional entre cliente(socket) y servidor<br>Trabaja en los puertos 8080 y 443";
			TextMessage mensaje = new TextMessage(prueba);
			session.sendMessage(mensaje);
		}
		
		mensajes.add(message.getPayload());
	}
}