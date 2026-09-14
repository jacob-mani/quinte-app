import { Component, OnInit } from '@angular/core';
import { ChangeDetectorRef } from '@angular/core';

import { Student } from '../../models/student.model';
import { StudentApiService } from '../../services/student-api.service';

@Component({
  selector: 'app-student-list',
  standalone: false,
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css']
})
export class StudentListComponent implements OnInit {
  students: Student[] = [];
  filteredStudents: Student[] = [];
  searchText = '';
  loading = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private readonly studentApiService: StudentApiService,
    private readonly cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadStudents();
  }

  loadStudents(): void {
    this.loading = true;
    this.errorMessage = '';

    this.studentApiService.getStudents().subscribe({
      next: (data) => {
        this.students = Array.isArray(data) ? data : [];
        this.applyFilter();
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (error) => {
        this.loading = false;
        this.errorMessage = error?.error?.message ?? 'Failed to load students.';
      }
    });
  }

  applyFilter(): void {
    const search = this.searchText.trim().toLowerCase();
    if (!search) {
      this.filteredStudents = [...this.students];
      console.log(this.filteredStudents);
      return;
    }

    this.filteredStudents = this.students.filter((student) =>
      (student.name ?? '').toLowerCase().includes(search) ||
      (student.email ?? '').toLowerCase().includes(search)
    );
  }

  clearMessages(): void {
    this.errorMessage = '';
    this.successMessage = '';
  }

  deleteStudent(student: Student): void {
    this.clearMessages();
    const confirmed = window.confirm(`Delete student ${student.name}?`);
    if (!confirmed) {
      return;
    }

    this.studentApiService.deleteStudent(student.id).subscribe({
      next: (response) => {
        this.successMessage = response.message;
        this.loadStudents();
      },
      error: (error) => {
        this.errorMessage = error?.error?.message ?? 'Failed to delete student.';
      }
    });
  }
}
