import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { StudentApiService } from '../../services/student-api.service';

@Component({
  selector: 'app-student-form',
  standalone: false,
  templateUrl: './student-form.component.html',
  styleUrls: ['./student-form.component.css']
})
export class StudentFormComponent {
  loading = false;
  successMessage = '';
  errorMessage = '';

  readonly studentForm = this.formBuilder.group({
    name: ['', [Validators.required, Validators.maxLength(100)]],
    email: ['', [Validators.required, Validators.email, Validators.maxLength(255)]],
    phoneNumber: ['', [Validators.required, Validators.pattern(/^[0-9]{10,15}$/)]]
  });

  constructor(
    private formBuilder: FormBuilder,
    private studentApiService: StudentApiService,
    private router: Router
  ) {}

  submit(): void {
    this.successMessage = '';
    this.errorMessage = '';

    if (this.studentForm.invalid) {
      this.studentForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    const payload:any = this.studentForm.getRawValue();

    this.studentApiService.createStudent(payload).subscribe({
      next: () => {
        this.loading = false;
        this.successMessage = 'Student created successfully.';
        this.studentForm.reset();
        this.router.navigate(['/students']);
      },
      error: (error) => {
        this.loading = false;
        this.errorMessage = error?.error?.message ?? 'Failed to create student.';
      }
    });
  }

  goToList(): void {
    this.router.navigate(['/students']);
  }
}
