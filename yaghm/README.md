![img_logo_big_filled.png](..%2Fdocs%2Fimg%2Fimg_logo_big_filled.png)

# YAGHM

## `:yaghm`

### Structure

YAGHM has a follow structure:

- `common` - contains domain-free code (can be applied (a.k.a. copy-pasted) with no problem);
- `internal` - domain logic; contains all logic for GitHook management. 

### Config

The configuration module in YAGHM is pivotal for customizing various aspects of the build distribution
process. Through carefully defined configurations, users can tailor YAGHM to suit their specific
requirements and environments.


### Networking

YAGHM harnesses the capabilities of http4k, a lightweight HTTP toolkit for Kotlin, to handle
networking tasks effectively. This choice offers several benefits, including high performance,
simplicity, and extensibility. Leveraging http4k allows YAGHM to effortlessly manage HTTP
communications, making it ideal for handling build distribution requests.


## Troubleshooting

Encountering issues while using YAGHM? Check out the troubleshooting section for common problems
and solutions. If you still need assistance, feel free to reach out to the YAGHM community
for support.

## Contributing

I welcome contributions from the community to help improve YAGHM. Whether you want to report a bug,
suggest a new feature, or submit a pull request, follow the contribution guidelines outlined in the
project's repository. Together, we can make YAGHM even better.

## License

MIT License

```
Copyright (c) [2024] [Andrew Malitchuk]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```