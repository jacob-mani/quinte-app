import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { Student, StudentCreateRequest, StudentUpdateRequest } from '../models/student.model';

@Injectable({
  providedIn: 'root'
})
export class StudentApiService {
  private readonly studentsUrl = `${environment.apiBaseUrl}/api/students`;

  constructor(private readonly http: HttpClient) {}

  createStudent(payload: StudentCreateRequest): Observable<Student> {
    return this.http.post<Student>(this.studentsUrl, payload);
  }

  getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(this.studentsUrl);
  }

  getStudentById(id: number): Observable<Student> {
    return this.http.get<Student>(`${this.studentsUrl}/${id}`);
  }

  updateStudent(id: number, payload: StudentUpdateRequest): Observable<Student> {
    return this.http.put<Student>(`${this.studentsUrl}/${id}`, payload);
  }

  deleteStudent(id: number): Observable<{ message: string }> {
    return this.http.delete<{ message: string }>(`${this.studentsUrl}/${id}`);
  }
}
