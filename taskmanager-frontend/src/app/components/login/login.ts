import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Auth } from '../../service/auth';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  imports: [FormsModule],        // ← hier hinzufügen
  templateUrl: './login.html',
  styleUrl: './login.css',
})

export class Login implements OnInit {

constructor(private auth: Auth, private router: Router){}

  ngOnInit(): void {
    if (localStorage.getItem('token')){
      this.router.navigate(['/projects']);
    }
  }

  username: string = "";
  password: string = "";

  login(): void {
    if (!this.username || !this.password){
      console.log("Bitte alle Felder ausfüllen");
    }else {

     this.auth.login(this.username, this.password);
    }
  }
}


