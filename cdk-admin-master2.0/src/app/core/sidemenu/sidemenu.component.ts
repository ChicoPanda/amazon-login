import { Component, OnInit, Input } from '@angular/core';
import { menus } from './menu-element';

@Component({
  selector: 'cdk-sidemenu',
  templateUrl: './sidemenu.component.html',
  styleUrls: ['./sidemenu.component.scss']
})
export class SidemenuComponent implements OnInit {

  img_url:String;

    @Input() iconOnly:boolean = false;
    public menus = menus;

    constructor() {
      this.img_url=localStorage.getItem("userImage");
     }
    
    ngOnInit() {
     
    }

}
