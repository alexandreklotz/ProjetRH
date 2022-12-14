import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map, Observable, switchMap} from "rxjs";
import {Question} from "../_models/question.model";


@Injectable({
  providedIn:'root'
})

export class QuestionService {

  constructor(private http: HttpClient) {}

  getAllQuestions():Observable<Question[]>{
    return this.http.get<Question[]>('http://localhost:8080/admin/question/all')
  }

  getQuestionById(questionId: string): Observable<Question>{
    return this.http.get<Question>(`http://localhost:8080/admin/question/${questionId}`)
  }

  getQuestionByQcmId(qcmId: string): Observable<Question[]>{
    return this.http.get<Question[]>(`http://localhost:8080/admin/question/qcm/${qcmId}`)
  }

}
