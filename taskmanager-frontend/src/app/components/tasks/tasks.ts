import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Task, TaskResponse } from '../../services/task';

@Component({
  selector: 'app-tasks',
  imports: [FormsModule],
  templateUrl: './tasks.html',
  styleUrl: './tasks.css',
})
export class Tasks implements OnInit {


  constructor(private taskService: Task, private route: ActivatedRoute){}

  projectId: number = 0;
  aufgaben = signal<TaskResponse[]>([]);
  title: string = '';
  description: string = '';
  status: string = 'OPEN';
  priority: string = 'LOW';
  dueDate: string = '';


  ngOnInit(): void {
    this.projectId = Number(this.route.snapshot.params['projectId']);
    this.ladeTasks();
  }

  ladeTasks(): void{
    this.taskService.getTasks(this.projectId).subscribe(daten =>{
      this.aufgaben.set(daten);
    });
}
erstelleTask(): void{
  if (!this.title){
    console.log("Bitte Aufgaben-Titel eingeben!")
  }else{
    this.taskService.createTask(this.title, this.description, this.status, this.priority, this.dueDate, this.projectId)
    .subscribe(() => {
      this.ladeTasks();
    });
  } 
}

loescheTask(id: number): void{
this.taskService.deleteTask(this.projectId, id).subscribe(() => {
  this.ladeTasks();
});
}
}
