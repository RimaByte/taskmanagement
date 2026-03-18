import { Component, OnInit, signal } from '@angular/core';
import { Project} from '../../services/project'



@Component({
  selector: 'app-projects',
  imports: [],
  templateUrl: './projects.html',
  styleUrl: './projects.css',
})
export class Projects implements OnInit {

  constructor(private projectService: Project){} 

  ngOnInit(): void {
    this.ladeProjekte();
  }

  projekte = signal<any[]>([]);

  ladeProjekte(): void{
    this.projectService.getProjects().subscribe(daten => {
      this.projekte.set(daten);
    });
  }
}
