import {Component, OnInit} from '@angular/core';
import {Client} from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import {FingerprintService} from "./fingerprint.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'nerf-front';
  private client!: Client;
  messageList : string[] = [];

  showRegister: Boolean = false;
  showLogin: Boolean = false;
  logged: Boolean = false;

  constructor(private fingerprintService: FingerprintService) {
  }

  ngOnInit(): void {
  }

  onRegister() {
    this.fingerprintService.enroll().subscribe(() =>{
      this.showRegister = true;
      this.showLogin = false;
      this.messageList = [];
      this.client = new Client();
      this.client.webSocketFactory = () => {
        return new SockJS('http://localhost:8080/ws');
      };
      this.client.onConnect = (frame) => {
        this.client.subscribe('/topic/message',  (event) => {
          this.messageList.push(event.body);
          if(event.body.startsWith("Stored!")) {
            this.client.deactivate();
            this.messageList = [];
            this.messageList.push("Registraddo correctamente");
          }
        });
      };
      this.client.activate();
    });
  }

  onLogin() {
    this.fingerprintService.check().subscribe(() =>{
      this.showLogin = true;
      this.showRegister = false;
      this.messageList = [];
      this.client = new Client();
      this.client.webSocketFactory = () => {
        return new SockJS('http://localhost:8080/ws');
      };
      this.client.onConnect = (frame) => {
        this.client.subscribe('/topic/message',  (event) => {
          this.messageList.push(event.body);
          if(event.body.startsWith("Match")) {
            this.client.deactivate();
            this.messageList = [];
            this.logged = true;
          }
        });
      };
      this.client.activate();
    });
  }

  onLogout() {
    this.showRegister = false;
    this.showLogin = false;
    this.logged = false;
    this.messageList = [];
  }

  onShoot() {
    this.fingerprintService.shoot().subscribe();
  }
}
