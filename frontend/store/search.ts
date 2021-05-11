import { Module, VuexAction, VuexModule, VuexMutation } from 'nuxt-property-decorator';
import { FieldSearchDto, FieldTypeDto } from '~/models/dto/FieldSearchDto';

@Module({
  name: 'search',
  stateFactory: true,
  namespaced: true,
})
export default class Search extends VuexModule {
  private _searchQuery: string = '';

  get searchQuery(): string {
    return this._searchQuery;
  }

  private _matchingQuery: Array<FieldSearchDto> = [];

  get matchingQuery(): Array<FieldSearchDto> {
    return this._matchingQuery;
  }

  @VuexAction
  setSearch(search: string): void {
    this.setSearchQuery(search);
    console.log("Search string update", search);
  }

  @VuexAction
  updateSearch() {
    const regex = /\S+:\S+/gm;
    let m;
    const textInQuoteRegex = /(["'])(?:\\.|[^\\])*?\1/gm;

    while ((m = textInQuoteRegex.exec(this.searchQuery)) !== null) {
      // This is necessary to avoid infinite loops with zero-width matches
      if (m.index === regex.lastIndex) {
        regex.lastIndex++;
      }
      // The result can be accessed through the `m`-variable.
      console.log(m);
      m.forEach((match, groupIndex) => {
        if (groupIndex == 0) {
          this.addToMatchingQuery(Search.stringToFieldTextSearch(match));
        }
      });
    }

    let str = this.searchQuery.replace(textInQuoteRegex, '');
    while ((m = regex.exec(str)) !== null) {
      // This is necessary to avoid infinite loops with zero-width matches
      if (m.index === regex.lastIndex) {
        regex.lastIndex++;
      }

      // The result can be accessed through the `m`-variable.
      m.forEach((match, groupIndex) => {
        this.addToMatchingQuery(Search.stringToFieldTagSearch(match));
      });
    }


    const value = this.searchQuery.replace(regex, '').trim();

    if (!Search.isBlank(value)) {
      this.addToMatchingQuery(Search.stringToFieldTextSearch(value));
    }

    this.setSearchQuery("");
  }

  @VuexAction
  removeFromQuery(index: number) {
    this.removeFromMatchingQuery(index);
  }

  @VuexMutation
  private setSearchQuery(search: string): void {
    this._searchQuery = search;
  }

  @VuexMutation
  private addToMatchingQuery(v: FieldSearchDto) {
    const match = this._matchingQuery.find(value => value.name === v.name && v.type === value.type);
    if (match) {
      for (let payload of v.values) {
        if (!match.values.includes(payload)) {
          match.values.push(payload);
        }
      }
    } else {
      this._matchingQuery.push(v);
    }
  }

  @VuexMutation
  private removeFromMatchingQuery(index: number) {
    this._matchingQuery.splice(index, 1);
  }

  private static isBlank(str: string) {
    return (!str || /^\s*$/.test(str));
  }

  private static stringToFieldTagSearch(v: string): FieldSearchDto {
    const tag = v.split(':');
    console.log(tag);
    return {
      name: tag[0].replace('-', ''),
      type: FieldTypeDto.Tag,
      values: [tag[1]],
      negation: tag[0].startsWith("-"),
    };
  };

  private static stringToFieldTextSearch(v: string): FieldSearchDto {
    return {
      name: "message",
      type: FieldTypeDto.FullText,
      values: [v],
      negation: v.startsWith("-"),
    };
  };
}
