import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { Task, TaskResponse } from '../../services/task';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

@Component({
  selector: 'app-tasks',
  imports: [FormsModule, DatePipe, MatCardModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule, MatDatepickerModule, MatNativeDateModule, MatToolbarModule],
  templateUrl: './tasks.html',
  styleUrl: './tasks.css',
})
export class Tasks implements OnInit {


  constructor(private taskService: Task, private route: ActivatedRoute, private router: Router){}

  projectId: number = 0;
  aufgaben = signal<TaskResponse[]>([]);
  title: string = '';
  description: string = '';
  status: string = 'OPEN';
  priority: string = 'LOW';
  dueDate: Date | null = null;


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
    const dueDateString = this.dueDate ? `${this.dueDate.getFullYear()}-${String(this.dueDate.getMonth() + 1).padStart(2, '0')}-${String(this.dueDate.getDate()).padStart(2, '0')}` : '';
    this.taskService.createTask(this.title, this.description, this.status, this.priority, dueDateString, this.projectId)
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

seiteZurueck(): void {
  this.router.navigate(['/projects']);
}
}
