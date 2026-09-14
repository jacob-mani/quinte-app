export interface Student {
  id: number;
  name: string;
  email: string;
  phoneNumber: string;
  createdDate: string;
}

export interface StudentCreateRequest {
  name: string;
  email: string;
  phoneNumber: string;
}

export interface StudentUpdateRequest {
  name?: string;
  email?: string;
  phoneNumber?: string;
}
