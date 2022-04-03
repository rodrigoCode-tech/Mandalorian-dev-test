import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TarefasService {

  urlApi = 'http://localhost:8080/api/tarefas'

  // Headers
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  constructor(
    private httpClient: HttpClient
  ) { }

  getTarefas(): Observable<any[]> {
    return this.httpClient.get<any[]>(this.urlApi)
      .pipe(
        retry(2),
        catchError(this.handleError))
  }

  save(tarefa: any): Observable<any> {
    return this.httpClient.post<any>(this.urlApi, JSON.stringify(tarefa), this.httpOptions)
      .pipe(
        catchError(this.handleError)
      )
  }

  update(tarefa: any): Observable<any> {
    return this.httpClient.put<any>(this.urlApi, JSON.stringify(tarefa), this.httpOptions)
    .pipe(
      catchError(this.handleError)
    )
  }

  get(titulo: any) {
    return this.httpClient.get<any>(this.urlApi + '/' + titulo, this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  delete(titulo: any) {
    return this.httpClient.delete<any>(this.urlApi + '/' + titulo, this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  handleError(ex: HttpErrorResponse) {
    console.log('erro', ex.error);
    return throwError(ex.error);
  };
}
