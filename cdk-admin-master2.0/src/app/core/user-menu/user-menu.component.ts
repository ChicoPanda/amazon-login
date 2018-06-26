import { Component, OnInit, Input, HostListener, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService, UserService } from '../../service';

@Component({
	selector: 'cdk-user-menu',
	templateUrl: './user-menu.component.html',
	styleUrls: ['./user-menu.component.scss']
})
export class UserMenuComponent implements OnInit {
	isOpen: boolean = false;
	img_url: string;
	//currentUser = null;
	Hari;


	@Input() currentUser = null;
	@HostListener('document:click', ['$event', '$event.target'])
	onClick(event: MouseEvent, targetElement: HTMLElement) {
		if (!targetElement) {
			return;
		}

		const clickedInside = this.elementRef.nativeElement.contains(targetElement);
		if (!clickedInside) {
			this.isOpen = false;
		}
	}


	constructor(private elementRef: ElementRef,
		private authService: AuthService,
		private router: Router,
		private userService: UserService
	) {
		this.img_url = localStorage.getItem("userImage");
	}

	ngOnInit() {
	}

	logout() {
		this.authService.logout().subscribe(res => {
			this.router.navigate(['/login']);
		});
	}
	userName() {
		return  localStorage.getItem("userName");

	}

}
