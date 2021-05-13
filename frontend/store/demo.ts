import {Module, VuexAction, VuexModule, VuexMutation} from 'nuxt-property-decorator';
import {Api} from '~/utils/api';

@Module({
  name: 'demo',
  stateFactory: true,
  namespaced: true,
})
export default class Demo extends VuexModule {

  private _users: any[] = [];
  get users(): string[] {
    return this._users;
  }


  @VuexAction
  async updateUser(): Promise<void> {
    try {
      const users = await Api.getUsers();

      this.setUsers(users);
    } catch (err) {
      console.error(err);
    }
  }


  @VuexMutation
  private setUsers(users: any[]): void {
    this._users = users;
  }


  @VuexAction
  async addUser(user: any): Promise<void> {
    try {
      await Api.addUser(user);
      await this.updateUser();
    } catch (err) {
      console.error(err);
    }
  }
  @VuexAction
  async addUsers(users: any[]): Promise<void> {
    try {
      for (let user of users) {
        await Api.addUser(user);
      }
      await this.updateUser();
    } catch (err) {
      console.error(err);
    }
  }

  @VuexAction
  async deleteAll(): Promise<void> {
    try {
      await this.updateUser();
      for (let user of this._users) {
        await Api.deleteUser(user.id);
      }
      await this.updateUser();
    } catch (err) {
      console.error(err);
    }
  }
  @VuexAction
  async deleteUser(user: number): Promise<void> {
    try {
      await Api.deleteUser(user);
      await this.updateUser();
    } catch (err) {
      console.error(err);
    }
  }
  @VuexAction
  async sendCustomLog(log: any): Promise<void> {
    try {
      await Api.postCustomLog(log);
    } catch (err) {
      console.error(err);
    }
  }

}
