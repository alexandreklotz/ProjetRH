import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {lastValueFrom, map, Observable, switchMap} from "rxjs";
import {Question} from "../../_models/question.model";
import {Utilisateur} from "../../_models/utilisateur.model";


@Injectable({
  providedIn:'root'
})

export class QuestionService {

  questionList$!: Question[]
  qcmQuestionsList$!: Question[]
  unassignedQuestionList$!: Question[]

  constructor(private http: HttpClient) {}

  async getAllQuestions(): Promise<Question[]>{
    let response = await lastValueFrom(this.http.get<Question[]>('http://localhost:8080/moderateur/question/all'))
    this.questionList$ = response
    return this.questionList$
  }

  async getQcmQuestions(): Promise<Question[]> {
    let response = await lastValueFrom(this.http.get<Question[]>('http://localhost:8080/moderateur/question/qcmquestions'))
    this.qcmQuestionsList$ = response
    return this.qcmQuestionsList$
  }

  async getUnassignedQuestion(): Promise<Question[]> {
    let response = await lastValueFrom(this.http.get<Question[]>('http://localhost:8080/moderateur/question/unassigned'))
    this.unassignedQuestionList$ = response
    return this.unassignedQuestionList$
  }

  getQuestionById(questionId: string): Observable<Question>{
    return this.http.get<Question>(`http://localhost:8080/moderateur/question/id/${questionId}`)
  }

  getQuestionByQcmId(qcmId: string): Observable<Question[]>{
    return this.http.get<Question[]>(`http://localhost:8080/admin/question/qcm/${qcmId}`)
  }

  createQuestion(data: any, headers : {headers : HttpHeaders}): Observable<Question> {
    //const jsonBody = JSON.stringify(question)
    return this.http.post<Question>('http://localhost:8080/moderateur/question/create', data, headers)
  }

  updateQuestion(data: any, headers : {headers : HttpHeaders}): Observable<Question>{
    return this.http.put<Question>('http://localhost:8080/moderateur/question/update', data, headers)
  }

  deleteQuestion(questionId: any){
    this.http.delete(`http://localhost:8080/moderateur/question/delete/${encodeURIComponent(questionId)}`).subscribe()
  }
}
