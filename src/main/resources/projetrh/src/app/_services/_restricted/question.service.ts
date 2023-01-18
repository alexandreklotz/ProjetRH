import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {lastValueFrom, map, Observable, switchMap} from "rxjs";
import {Question} from "../../_models/question.model";


@Injectable({
  providedIn:'root'
})

export class QuestionService {

  questionList$!: Question[]
  unassignedQuestionList$!: Question[]

  constructor(private http: HttpClient) {}

  async getAllQuestions(): Promise<Question[]>{
    let response = await lastValueFrom(this.http.get<Question[]>('http://localhost:8080/moderateur/question/all'))
    this.questionList$ = response
    return this.questionList$
  }

  async getUnassignedQuestion(): Promise<Question[]> {
    let response = await lastValueFrom(this.http.get<Question[]>('http://localhost:8080/moderateur/question/unassigned'))
    this.unassignedQuestionList$ = response
    return this.unassignedQuestionList$
  }

  getQuestionById(questionId: string): Observable<Question>{
    return this.http.get<Question>(`http://localhost:8080/admin/question/${questionId}`)
  }

  getQuestionByQcmId(qcmId: string): Observable<Question[]>{
    return this.http.get<Question[]>(`http://localhost:8080/admin/question/qcm/${qcmId}`)
  }

  createQuestion(question: Question): void {
    const jsonBody = JSON.stringify(question)
    this.http.post<Question>('http://localhost:8080/moderateur/question/create', jsonBody)
  }
}
