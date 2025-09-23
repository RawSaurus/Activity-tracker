/* tslint:disable */
/* eslint-disable */
import { EntityModelTemplateResponse } from '../models/entity-model-template-response';
import { PageableObject } from '../models/pageable-object';
import { SortObject } from '../models/sort-object';
export interface PageEntityModelTemplateResponse {
  content?: Array<EntityModelTemplateResponse>;
  empty?: boolean;
  first?: boolean;
  last?: boolean;
  number?: number;
  numberOfElements?: number;
  pageable?: PageableObject;
  size?: number;
  sort?: SortObject;
  totalElements?: number;
  totalPages?: number;
}
