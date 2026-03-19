import { Component, OnInit, signal } from '@angular/core';
import { Project} from '../../services/project'
import { Auth } from '../../service/auth';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';




@Component({
  selector: 'app-projects',
  imports: [FormsModule],
  templateUrl: './projects.html',
  styleUrl: './projects.css',
})
export class Projects implements OnInit {

  constructor(private projectService: Project, private auth: Auth, private router: Router){} 

  name: string = "";
  description: string = "";

  ngOnInit(): void {
    this.ladeProjekte();
  }

  projekte = signal<any[]>([]);

  zeigeAufgaben(projektId: number): void{
    this.router.navigate(['/projects', projektId, 'tasks']);
  }

  ladeProjekte(): void{
    this.projectService.getProjects().subscribe(daten => {
      this.projekte.set(daten);
    });
  }

  logout(): void{
    this.auth.logout();
  }

erstelleProjekt(): void{

  if (!this.name){
    console.log("Projektname angeben!");

  }else {
      this.projectService.createProject(this.name, this.description)
  .subscribe(() => {
    this.ladeProjekte();
  });
  }
}

loescheProjekt(id: number): void{
    this.projectService.loescheProjekt(id).subscribe(() => {
      this.ladeProjekte();
    });
}
}
