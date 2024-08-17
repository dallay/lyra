export interface IForm {
  readonly id: string,
  readonly name: string,
  readonly header: string,
  readonly description: string,
  readonly inputPlaceholder: string,
  readonly buttonText: string,
  readonly buttonColor: string,
  readonly backgroundColor: string,
  readonly textColor: string,
  readonly buttonTextColor: string,
  readonly createdAt: Date,
  readonly updatedAt: Date
}


export interface IUser {
  readonly id: string,
  readonly name: string,
  readonly username: string,
  readonly email: string,
  readonly roles: string[]
}
