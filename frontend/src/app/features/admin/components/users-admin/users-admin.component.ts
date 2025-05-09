import {Component, inject, OnInit} from '@angular/core';
import {AdminService} from '../../admin.service';
import {User} from '../../../../models/user.model';
import {
  MatCell, MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef,
  MatRow, MatRowDef,
  MatTable
} from '@angular/material/table';
import {MatButton, MatIconButton} from '@angular/material/button';
import {RouterLink} from '@angular/router';
import {MatIconModule} from '@angular/material/icon';

@Component({
  selector: 'app-users-admin',
  imports: [
    MatTable,
    MatHeaderCell,
    MatCell,
    MatHeaderRow,
    MatRow,
    MatColumnDef,
    MatHeaderCellDef,
    MatCellDef,
    MatButton,
    MatHeaderRowDef,
    MatRowDef,
    MatIconModule,
    MatIconButton,
    RouterLink
  ],
  templateUrl: './users-admin.component.html',
  styleUrl: './users-admin.component.css'
})
export class UsersAdminComponent implements OnInit {
  service = inject(AdminService);
  users: User[] = [];
  displayedColumns: string[] = ['id', 'name', 'email', 'actions'];

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    this.service.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
      },
      error: (error) => {
        console.error('Error loading users:', error);
      }
    });
  }

  deleteUser(userId: number) {
    this.service.deleteUser(userId).subscribe({
      next: () => {
        this.users = this.users.filter(u => u.id !== userId);
      },
      error: (error) => {
        console.error('Error deleting user:', error);
      }
    });
  }
}
