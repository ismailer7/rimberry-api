import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RawMaterialReceptionComponent } from './raw-material-reception.component';

describe('RawMaterialReceptionComponent', () => {
  let component: RawMaterialReceptionComponent;
  let fixture: ComponentFixture<RawMaterialReceptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RawMaterialReceptionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RawMaterialReceptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
