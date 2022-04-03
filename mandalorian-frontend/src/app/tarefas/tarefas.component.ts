import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { TarefasService } from './tarefas.service';

@Component({
  selector: 'app-tarefas',
  templateUrl: './tarefas.component.html',
  styleUrls: ['./tarefas.component.css']
})
export class TarefasComponent implements OnInit {

  tarefas: any = ['teste1','teste2','teste3']

  msgErro: any
  isError = false
  tarefa: any = {}

  constructor(
    private tarefasService: TarefasService
  ) { }

  ngOnInit(): void {
    this.getTarefas()
  }

  getTarefas(){
    this.tarefasService.getTarefas().subscribe((result: any) => {
      this.tarefas = result
    }, (e) => this.handlerErro(e))
  }

  edit(titulo: any){
    this.tarefasService.get(titulo).subscribe((result: any) => {
      this.tarefa = {...result}
      this.tarefa.isEdit = true
    }, (e) => this.handlerErro(e))
  }

  delete(titulo: any){
    this.tarefasService.delete(titulo).subscribe(() => {
      this.getTarefas();
    }, (e) => this.handlerErro(e));
  }

  save(form: NgForm) {
      if (!this.tarefa.isEdit) {
        this.tarefasService.save(this.tarefa).subscribe(() => {
          this.cleanForm(form);
        }, (e) => this.handlerErro(e));
      } else {
        this.tarefasService.update(this.tarefa).subscribe(() => {
          this.cleanForm(form);
        }, (e) => this.handlerErro(e));
      }
  }

  // limpa o formulario
  cleanForm(form: NgForm) {
    this.getTarefas()
    form.resetForm();
    this.isError = false;
    this.tarefa = {} as any;
  }

  handlerErro(msgErro: any) {
    this.msgErro = msgErro;
    this.isError = true;
  }

}
