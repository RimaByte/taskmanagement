import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Auth } from '../../service/auth';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';



@Component({
  selector: 'app-login',
  imports: [FormsModule, MatCardModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatIconModule],
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
  fehler: string = "";
  hide = signal(true);


  login(): void {
    if (!this.username || !this.password){
      this.fehler = "Bitte alle Felder ausfüllen";
    }else {

     this.auth.login(this.username, this.password, (nachricht) => {
      this.fehler = nachricht;
     });
    }
  }

  zumRegister(): void{
    this.router.navigate(['/register']);
  }

clickEvent(event: MouseEvent) {
  this.hide.set(!this.hide());
  event.stopPropagation();
}

}


