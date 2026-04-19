import { Component, inject, OnInit } from '@angular/core';
import { NgClass } from '@angular/common';
import { AdminService } from '../../admin.service';
import { User } from '../../../../models/user.model';
import { MatIconButton } from '@angular/material/button';
import { RouterLink } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { ToastService } from '../../../../core/services/toast.service';
import { TranslatePipe } from '../../../../core/pipes/translate.pipe';
import { TranslationService } from '../../../../core/services/translation.service';

@Component({
  selector: 'app-users-admin',
  imports: [
    NgClass,
    MatIconModule,
    MatIconButton,
    RouterLink,
    MatProgressSpinner,
    TranslatePipe
  ],
  templateUrl: './users-admin.component.html',
  styleUrl: './users-admin.component.css'
})
export class UsersAdminComponent implements OnInit {
  service = inject(AdminService);
  private toast = inject(ToastService);
  private ts = inject(TranslationService);
  users: User[] = [];
  loaded = false;
  pendingDelete: User | null = null;

  private normalizeRole(role: any): string {
    const raw = (role ?? '').toString().toUpperCase().replace(/^ROLE_/, '');
    return raw || 'UNKNOWN';
  }

  roleLabel(role: any): string {
    const r = this.normalizeRole(role);
    return this.ts.translate('role.' + r.toLowerCase());
  }

  roleClass(role: any): string {
    return `role-badge--${this.normalizeRole(role).toLowerCase()}`;
  }

  askDelete(user: User) {
    this.pendingDelete = user;
  }

  cancelDelete() {
    this.pendingDelete = null;
  }

  confirmDelete() {
    if (!this.pendingDelete) return;
    const user = this.pendingDelete;
    this.pendingDelete = null;
    this.deleteUser(user.id);
  }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    this.service.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
        this.loaded = true;
      },
      error: (error) => {
        console.error('Error loading users:', error);
        this.loaded = true;
        this.toast.error(this.ts.translate('admin.users.toast.loadError'));
      }
    });
  }

  deleteUser(userId: any) {
    this.service.deleteUser(Number(userId)).subscribe({
      next: () => {
        this.users = this.users.filter(u => u.id !== userId);
        this.toast.success(this.ts.translate('admin.users.toast.deleted'));
      },
      error: (error) => {
        console.error('Error deleting user:', error);
        this.toast.error(this.ts.translate('admin.users.toast.deleteError'));
      }
    });
  }
}
