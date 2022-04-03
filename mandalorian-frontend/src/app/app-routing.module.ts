import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TarefasComponent } from './tarefas/tarefas.component';

const routes: Routes = [
  {
    path: 'tarefas',
    component: TarefasComponent,
    data: { title: 'Tarefas'}
  },
  {
    path: '',
    redirectTo: '/tarefas',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { 
  
}
